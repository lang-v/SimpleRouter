package com.sl.router.processor;

import com.sl.router.annotation.CreateMethod;
import com.sl.router.annotation.Router;
import com.sl.router.table.RouterInfo;
import com.sl.router.table.RouterInformationDuplicateException;
import com.sl.router.table.RouterTable;
import com.sl.router.util.AnnotationMirrorUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Set;


/**
 * 注解处理器采集信息，信息写入router.manifest
 */
//@AutoService(Processor.class)
@SupportedAnnotationTypes({
        "com.sl.router.annotation.Router"
})
public class RouterProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        RouterTable table = new RouterTable();
        for (Element element : roundEnv.getElementsAnnotatedWith(Router.class)) {
            if (element instanceof TypeElement) {
                String clazz = ((TypeElement) element).getQualifiedName().toString();
                RouterInfo info = getRouterInfo(element, clazz);
                ln(info.toString());
                try {
                    table.add(info);
                } catch (RouterInformationDuplicateException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        if (table.size() > 0) {
            creatFile(table);
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    private RouterInfo getRouterInfo(Element element, String clazz) {
        String path = AnnotationMirrorUtils.INSTANCE.getValue(element, Router.class, "path");
        Object service = AnnotationMirrorUtils.INSTANCE.getValue(element, Router.class, "service");
        Object method = AnnotationMirrorUtils.INSTANCE.getValue(element, Router.class, "method");
        return new RouterInfo(clazz, path, service.toString(), method.toString());
    }

    private void creatFile(RouterTable r) {
        try {
            FileObject jfo = filer.createResource(StandardLocation.CLASS_OUTPUT, "router", "Router.properties", new Element[]{});
            Writer writer = jfo.openWriter();
            StringBuilder sb = new StringBuilder();
            for (RouterInfo value : r.values()) {
                sb.append(value.toString());
                sb.append("\n");
            }
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            ln("router info saved in " + jfo.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void ln(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
