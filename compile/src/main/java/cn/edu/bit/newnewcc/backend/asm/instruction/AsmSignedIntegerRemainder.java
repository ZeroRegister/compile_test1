package cn.edu.bit.newnewcc.backend.asm.instruction;

import cn.edu.bit.newnewcc.backend.asm.operand.IntRegister;
import cn.edu.bit.newnewcc.backend.asm.operand.Register;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 有符号整数求余数运算
 */
public class AsmSignedIntegerRemainder extends AsmInstruction {
    public enum Opcode {
        REM("rem"),
        REMW("remw");

        private final String name;

        Opcode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private final Opcode opcode;

    public AsmSignedIntegerRemainder(IntRegister dest, IntRegister source1, IntRegister source2, int bitLength) {
        super(Objects.requireNonNull(dest), Objects.requireNonNull(source1), Objects.requireNonNull(source2));

        if (bitLength != 32 && bitLength != 64)
            throw new IllegalArgumentException();

        if (bitLength == 64) opcode = Opcode.REM;
        else opcode = Opcode.REMW;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    @Override
    public String toString() {
        return String.format("AsmSignedIntegerRemainder(%s, %s, %s, %s)", getOpcode(), getOperand(1), getOperand(2), getOperand(3));
    }

    @Override
    public String emit() {
        return String.format("\t%s %s, %s, %s\n", getOpcode().getName(), getOperand(1).emit(), getOperand(2).emit(), getOperand(3).emit());
    }

    @Override
    public Set<Register> getDef() {
        return Set.of((Register) getOperand(1));
    }

    @Override
    public Set<Register> getUse() {
        return Set.copyOf(List.of((Register) getOperand(2), (Register) getOperand(3)));
    }

    @Override
    public boolean mayNotReturn() {
        return false;
    }

    @Override
    public boolean willNeverReturn() {
        return false;
    }

    @Override
    public boolean mayHaveSideEffects() {
        return false;
    }
}
