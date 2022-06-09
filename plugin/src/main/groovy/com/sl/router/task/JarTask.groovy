package com.sl.router.task

import com.android.build.gradle.internal.publishing.AndroidArtifacts
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.ArtifactView
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.file.FileTree
import org.gradle.api.logging.Logger
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/**
 * 在这里处理所有通过gradle管理的依赖
 * 所有的jar包中的Router.properties会被提取出来
 * 然而这里依赖的获取方式受到项目中的依赖方式限制，如：compile、implementation
 * compile 是可以被我们解析的
 * implementation 默认是不可解析的，虽然可以通过这个方式开启解析 {@link Configuration.setCanBeResolved(boolean)}
 * 但是项目中往往存在多种依赖方式，我们不应该去限制他们
 *
 * 目前想到两个方案：
 * 1. 自定义依赖方式，但是又违背了便捷的本意
 * 2. 采用TransformTask来解析所有的class、JAR以及AAR
 *
 * @see CollectRouterInfo
 * @date 2022-05-30
 */
@Deprecated
public class JarTask extends DefaultTask {

    private Logger logger
    private Project project

    @OutputFile
    private File collectPath

    @Override
    public String getName(){
        return "JarTask"
    }

    public JarTask() {
        project = getProject()
        logger = getLogger()
        collectPath = project.file(project.getBuildDir().toString()+File.separator+"generated"+File.separator+"router-collect")
        ln(collectPath.toString())
        if(collectPath.exists()) {
            collectPath.deleteDir()
            collectPath.mkdirs()
        }else {
            collectPath.mkdirs()
        }
    }

    @TaskAction
    void start() {
        ln("JarTask started.")
        ln(getProject().getBuildDir().toString())
        Configuration implementation = project.getConfigurations().getByName("implementation")

        // implementation 无法直接解析，compile可以
        implementation.setCanBeResolved(true)

        ResolvableDependencies resolvableDependencies = implementation.getIncoming()
        ArtifactView view = resolvableDependencies.artifactView { conf ->
            conf.attributes { attributeContainer ->
                attributeContainer.attribute(AndroidArtifacts.ARTIFACT_TYPE, AndroidArtifacts.ArtifactType.JAR.getType())
            }

        }

        var count = 0

        for (File file : view.getFiles()) {
            ln(file.getName())
            FileTree tree = project.zipTree(file)
            Set<File> files = tree.matching {
                include "Router.properties"
            }.getFiles()
            if (files.size() > 0) {
                count++
                File dest = project.file(collectPath.toString()+File.separator+file.name+"-"+files[0].name)
                copyFile(files[0].toPath(),dest.toPath())
                ln("find route " + dest)
            }
        }

        ln("total collect "+count+" Router.properties")
    }

    void copyFile(Path source, Path dest){
        Files.copy(source,dest, StandardCopyOption.REPLACE_EXISTING)
    }

    void ln(String msg) {
//        logger.debug(msg)
        project.println(getName()+">"+msg)
    }
}
