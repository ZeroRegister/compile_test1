package cn.edu.bit.newnewcc.ir.value.instruction;

import cn.edu.bit.newnewcc.ir.Operand;
import cn.edu.bit.newnewcc.ir.Value;
import cn.edu.bit.newnewcc.ir.exception.IllegalArgumentException;
import cn.edu.bit.newnewcc.ir.type.IntegerType;
import cn.edu.bit.newnewcc.ir.value.Instruction;

import java.util.ArrayList;
import java.util.List;

/**
 * 零拓展指令
 * <p>
 * 在高位补0，将位数较少的整型转化为位数较多的整型
 *
 * @see <a href="https://llvm.org/docs/LangRef.html#zext-to-instruction" />LLVM IR文档</a>
 */
public class ZeroExtensionInst extends Instruction {

    private final Operand sourceOperand;

    public ZeroExtensionInst(IntegerType sourceType, IntegerType targetType) {
        super(targetType);
        if (sourceType.getBitWidth() >= targetType.getBitWidth()) {
            throw new IllegalArgumentException("Source type cannot have more bits than target type");
        }
        this.sourceOperand = new Operand(this, sourceType, null);
    }

    public ZeroExtensionInst(Value sourceValue, IntegerType targetType) {
        super(targetType);
        if (!(sourceValue.getType() instanceof IntegerType)) {
            throw new java.lang.IllegalArgumentException("Source value must be of integer type");
        }
        if (((IntegerType) sourceValue.getType()).getBitWidth() >= targetType.getBitWidth()) {
            throw new IllegalArgumentException("Source type cannot have more bits than target type");
        }
        this.sourceOperand = new Operand(this, sourceValue.getType(), sourceValue);
    }

    public Value getSourceOperand() {
        return sourceOperand.getValue();
    }

    public void setSourceOperand(Value value) {
        sourceOperand.setValue(value);
    }

    public IntegerType getSourceType() {
        return (IntegerType) sourceOperand.getType();
    }

    public IntegerType getTargetType() {
        return getType();
    }

    @Override
    public IntegerType getType() {
        return (IntegerType) super.getType();
    }

    @Override
    public List<Operand> getOperandList() {
        var list = new ArrayList<Operand>();
        list.add(sourceOperand);
        return list;
    }

    @Override
    public void emitIr(StringBuilder builder) {
        builder.append(String.format(
                "%s = zext %s %s to %s",
                this.getValueNameIR(),
                getSourceType().getTypeName(),
                getSourceOperand().getValueNameIR(),
                getTargetType().getTypeName()
        ));
    }
}
