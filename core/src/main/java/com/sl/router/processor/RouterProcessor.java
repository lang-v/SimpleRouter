package com.sl.router.processor;

import com.sl.router.annotation.Router;
import com.sl.router.table.RouterInformationDuplicateException;
import com.sl.router.table.RouterTable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.HashMap;
import java.util.Set;


/**
 * 注解处理器采集信息，信息写入router.manifest
 */
@SupportedAnnotationTypes({
        "com.sl.router.annotation.Router"
})
public class RouterProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private Elements elementUtils;
    private RouterTable table;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(Router.class)) {
            // element.getKind() == ElementKind.CLASS 仅限类
            if (element instanceof TypeElement){
                try {
                    table.add((TypeElement) element);
                } catch (RouterInformationDuplicateException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }


    private void ln(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE,msg);
    }
}
