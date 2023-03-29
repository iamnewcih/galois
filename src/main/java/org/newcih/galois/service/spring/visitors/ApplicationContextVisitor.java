/*
 * MIT License
 *
 * Copyright (c) [2023] [liuguangsheng]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.newcih.galois.service.spring.visitors;

import java.util.Objects;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import org.newcih.galois.service.MethodAdapter;
import org.newcih.galois.service.spring.SpringAgentService;
import org.newcih.galois.service.spring.SpringBeanReloader;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

import static jdk.internal.org.objectweb.asm.Opcodes.ALOAD;
import static jdk.internal.org.objectweb.asm.Opcodes.ASM5;
import static jdk.internal.org.objectweb.asm.Opcodes.ATHROW;
import static jdk.internal.org.objectweb.asm.Opcodes.CHECKCAST;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKESTATIC;
import static jdk.internal.org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static jdk.internal.org.objectweb.asm.Opcodes.IRETURN;
import static jdk.internal.org.objectweb.asm.Opcodes.RETURN;
import static org.newcih.galois.constants.ClassNameConstant.ANNOTATION_CONFIG_SERVLET_WEB_SERVER_APPLICATION_CONTEXT;
import static org.newcih.galois.constants.Constant.DOT;
import static org.newcih.galois.constants.Constant.SLASH;


/**
 * 用于SpringBoot上下文嵌入的Visitor类
 *
 * @author liuguangsheng
 * @since 1.0.0
 */
public class ApplicationContextVisitor extends MethodAdapter {

    static {
        SpringAgentService.getInstance().registerMethodAdapter(new ApplicationContextVisitor());
    }

    /**
     * Instantiates a new Application context visitor.
     */
    private ApplicationContextVisitor() {
        super(ANNOTATION_CONFIG_SERVLET_WEB_SERVER_APPLICATION_CONTEXT);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        if (Objects.equals("<init>", name) && Objects.equals(descriptor, "()V")) {
            return new ConstructorVisiter(ASM5, mv);
        }

        return mv;
    }

    public interface NecessaryMethods {

        void setContext(AnnotationConfigServletWebServerApplicationContext context);
    }

    /**
     * The type Constructor visiter.
     */
    class ConstructorVisiter extends MethodVisitor {

        /**
         * Instantiates a new Constructor visiter.
         *
         * @param api           the api
         * @param methodVisitor the method visitor
         */
        public ConstructorVisiter(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                String pClassName = SpringBeanReloader.class.getName().replace(DOT, SLASH);
                String vClassName = className.replace(DOT, SLASH);

                mv.visitMethodInsn(INVOKESTATIC, pClassName, "getInstance", "()L" + pClassName + ";",
                        false);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitTypeInsn(CHECKCAST, vClassName);
                mv.visitMethodInsn(INVOKEVIRTUAL, pClassName, "setContext", "(L" + vClassName + ";)V",
                        false);
            }

            super.visitInsn(opcode);
        }
    }

}
