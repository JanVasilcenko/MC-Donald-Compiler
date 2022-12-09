package encoder;

import ast.*;
import tam.Instruction;
import tam.Machine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;

public class Encoder implements Visitor {
    private int nextAdr = Machine.CB;
    private int currentLevel = 0;

    private void emit(int op, int n, int r, int d) {
        if (n > 255) {
            System.out.println("Operand too long");
            n = 255;
        }

        Instruction instr = new Instruction();
        instr.op = op;
        instr.n = n;
        instr.r = r;
        instr.d = d;

        if (nextAdr >= Machine.PB)
            System.out.println("Program too large");
        else
            Machine.code[nextAdr++] = instr;
    }

    private void patch(int adr, int d) {
        Machine.code[adr].d = d;
    }

    private int displayRegister(int currentLevel, int entityLevel) {
        if (entityLevel == 0)
            return Machine.SBr;
        else if (currentLevel - entityLevel <= 6)
            return Machine.LBr + currentLevel - entityLevel;
        else {
            System.out.println("Accessing across to many levels");
            return Machine.L6r;
        }
    }

    public void saveTargetProgram(String fileName) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));

            System.out.println("***TAM***");
            for (int i = Machine.CB; i < nextAdr; ++i) {
                System.out.println(Machine.code[i]);
                Machine.code[i].write(out);
            }

            out.close();

            var inStream = new DataInputStream((new FileInputStream(fileName)));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trouble writing " + fileName);
        }
    }


    public void encode(Program p) {
        p.visit(this, null);
    }

    @Override
    public Object visitProgram(Program program, Object arg) {
        currentLevel = 0;

        program.block.visit(this, new Address());

        emit(Machine.HALTop, 0, 0, 0);

        return null;
    }

    @Override
    public Object visitBlock(Block block, Object arg) {
        int before = nextAdr;
        emit(Machine.JUMPop, 0, Machine.CB, 0);

        int size = ((Integer) block.decs.visit(this, arg)).intValue();

        patch(before, nextAdr);

        if (size > 0) {
            emit(Machine.PUSHop, 0, 0, size);
        }

        block.stats.visit(this, null);

        return size;
    }


    @Override
    public Object visitDeclarations(Declarations declarations, Object arg) {
        int startDisplacement = ((Address) arg).displacement;

        for (Declaration dec : declarations.dec) {
            arg = dec.visit(this, arg);
        }

        Address adr = (Address) arg;
        int size = adr.displacement - startDisplacement;

        return Integer.valueOf(size);
    }

    @Override
    public Object visitVariableDeclaration(VariableDeclaration variableDeclaration, Object arg) {
        variableDeclaration.address = (Address) arg;

        return new Address((Address) arg, 1);
    }

    @Override
    public Object visitFunctionDeclaration(FunctionDeclaration functionDeclaration, Object arg) {
        functionDeclaration.address = new Address(currentLevel, nextAdr);

        ++currentLevel;

        Address adr = new Address((Address) arg);

        int size = 0;
        if (functionDeclaration.params != null) {
            size = ((Integer) functionDeclaration.params.visit(this, adr)).intValue();
            functionDeclaration.params.visit(this, new Address(adr, -size));
        }

        functionDeclaration.block.visit(this, new Address(adr, Machine.linkDataSize));
        functionDeclaration.retExp.visit(this, Boolean.TRUE);

        emit(Machine.RETURNop, 1, 0, size);

        currentLevel--;

        return arg;
    }

    @Override
    public Object visitStatements(Statements statements, Object arg) {
        for (Statement stat : statements.stat)
            stat.visit(this, null);

        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatement, Object arg) {
        expressionStatement.exp.visit(this, Boolean.FALSE);

        return null;
    }

    @Override
    public Object visitIfStatement(IfStatement ifStatement, Object arg) {
        ifStatement.exp.visit(this, Boolean.TRUE);

        int jump1Adr = nextAdr;
        emit(Machine.JUMPIFop, 0, Machine.CBr, 0);

        ifStatement.thenPart.visit(this, null);

        int jump2Adr = nextAdr;
        emit(Machine.JUMPop, 0, Machine.CBr, 0);

        patch(jump1Adr, nextAdr);

        ifStatement.elsePart.visit(this, null);

        patch(jump2Adr, nextAdr);

        return null;
    }

    @Override
    public Object visitWhileStatement(WhileStatement whileStatement, Object arg) {
        int startAdr = nextAdr;

        whileStatement.exp.visit(this, Boolean.TRUE);

        int jumpAdr = nextAdr;
        emit(Machine.JUMPIFop, 0, Machine.CBr, 0);

        whileStatement.stats.visit(this, null);

        emit(Machine.JUMPop, 0, Machine.CBr, startAdr);
        patch(jumpAdr, nextAdr);

        return null;
    }

    @Override
    public Object visitSayStatement(SayStatement sayStatement, Object arg) {
        sayStatement.exp.visit(this, Boolean.TRUE);

        emit(Machine.CALLop, 0, Machine.PBr, Machine.putintDisplacement);
        emit(Machine.CALLop, 0, Machine.PBr, Machine.puteolDisplacement);

        return null;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression binaryExpression, Object arg) {
        boolean valueNeeded = ((Boolean) arg).booleanValue();

        String op = (String) binaryExpression.operator.visit(this, null);

        if (op.equals("=")) {
            Address adr = (Address) binaryExpression.operand1.visit(this, Boolean.FALSE);
            binaryExpression.operand2.visit(this, Boolean.TRUE);

            int register = displayRegister(currentLevel, adr.level);
            emit(Machine.STOREop, 1, register, adr.displacement);

            if (valueNeeded)
                emit(Machine.LOADop, 1, register, adr.displacement);
        } else {
            binaryExpression.operand1.visit(this, arg);
            binaryExpression.operand2.visit(this, arg);

            if (valueNeeded)
                if (op.equals("+"))
                    emit(Machine.CALLop, 0, Machine.PBr, Machine.addDisplacement);
                else if (op.equals("-"))
                    emit(Machine.CALLop, 0, Machine.PBr, Machine.subDisplacement);
                else if (op.equals("*"))
                    emit(Machine.CALLop, 0, Machine.PBr, Machine.multDisplacement);
                else if (op.equals("/"))
                    emit(Machine.CALLop, 0, Machine.PBr, Machine.divDisplacement);
                else if (op.equals("%"))
                    emit(Machine.CALLop, 0, Machine.PBr, Machine.modDisplacement);
        }

        return null;
    }

    @Override
    public Object visitVarExpression(VarExpression varExpression, Object arg) {
        boolean valueNeeded = ((Boolean) arg).booleanValue();

        Address adr = varExpression.declaration.address;
        int register = displayRegister(currentLevel, adr.level);

        if (valueNeeded)
            emit(Machine.LOADop, 1, register, adr.displacement);

        return adr;
    }

    @Override
    public Object visitCallExpression(CallExpression callExpression, Object arg) {
        boolean valueNeeded = ((Boolean) arg).booleanValue();

        callExpression.args.visit(this, null);

        Address adr = new Address();
        if (callExpression.functionDeclaration != null) {
            adr = callExpression.functionDeclaration.address;
        }

        int register = displayRegister(currentLevel, adr.level);

        emit(Machine.CALLop, register, Machine.CB, adr.displacement);

        if (!valueNeeded)
            emit(Machine.POPop, 0, 0, 1);

        return null;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression unaryExpression, Object arg) {
        boolean valueNeeded = ((Boolean) arg).booleanValue();

        String op = (String) unaryExpression.operator.visit(this, null);
        unaryExpression.operand.visit(this, arg);

        if (valueNeeded && op.equals("-"))
            emit(Machine.CALLop, 0, Machine.PBr, Machine.negDisplacement);

        return null;
    }

    @Override
    public Object visitIntLitExpression(IntLitExpression intLitExpression, Object arg) {
        boolean valueNeeded = ((Boolean) arg).booleanValue();

        Integer lit = (Integer) intLitExpression.literal.visit(this, null);

        if (valueNeeded)
            emit(Machine.LOADLop, 1, 0, lit.intValue());

        return null;
    }

    @Override
    public Object visitExpList(ExpList expList, Object arg) {
        for (Expression exp : expList.exp)
            exp.visit(this, Boolean.TRUE);

        return null;
    }

    @Override
    public Object visitIdentifier(Identifier identifier, Object arg) {
        return null;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteral integerLiteral, Object arg) {
        return Integer.valueOf(integerLiteral.spelling);
    }

    @Override
    public Object visitOperator(Operator operator, Object arg) {
        return operator.spelling;
    }
}
