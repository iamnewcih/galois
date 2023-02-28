package org.newcih.service.agent.frame.spring;

import org.newcih.service.agent.MethodAdapter;
import org.newcih.utils.GaloisLog;
import org.objectweb.asm.MethodVisitor;

import java.util.Objects;

import static org.objectweb.asm.Opcodes.*;

public class BeanDefinitionScannerVisitor extends MethodAdapter {

    private static final GaloisLog logger = GaloisLog.getLogger(BeanDefinitionScannerVisitor.class);

    public BeanDefinitionScannerVisitor() {
        super(SpringTransformer.CLASS_PATH_BEAN_DEFINITION_SCANNER);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        if (Objects.equals("doScan", name)) {
            return new DoScanMethodVisitor(ASM9, mv);
        }

        return mv;
    }

    static class DoScanMethodVisitor extends MethodVisitor {

        public DoScanMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {

                mv.visitCode();
                mv.visitMethodInsn(INVOKESTATIC, "org/newcih/service/agent/frame/spring/SpringBeanReloader",
                        "getInstance"
                        , "()Lorg/newcih/service/agent/frame/spring/SpringBeanReloader;", false);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKEVIRTUAL, "org/newcih/service/agent/frame/spring/SpringBeanReloader",
                        "setScanner"
                        , "(Lorg/springframework/context/annotation/ClassPathBeanDefinitionScanner;)V", false);
                mv.visitInsn(RETURN);
                mv.visitMaxs(2, 2);
                mv.visitEnd();

                if (logger.isDebugEnabled()) {
                    logger.debug("inject doScan method by asm success !");
                }

            }
        }
    }

}
