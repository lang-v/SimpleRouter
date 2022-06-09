package com.sl.router

import com.android.build.gradle.AppExtension
import com.sl.router.task.JarTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class RouterPlugin implements Plugin<Project> {

    private Project project

    @Override
    void apply(Project project) {
        this.project = project
        ln("init start")
        if (project.plugins.hasPlugin('com.android.application')) {
            ln('is application main project')
        }else if (project.plugins.hasPlugin('com.android.library')){
            ln("is library")
        }
        project.getExtensions().getByType(AppExtension).registerTransform(new CollectRouterInfo(project))

//        Task collect = project.tasks.create("RouterCollect",JarTask)
//
//        project.afterEvaluate {
//            project.tasks.findAll {
//                return it.name.startsWith("javaPreCompile")
//            }[0].dependsOn(collect)
//        }
    }

    void ln(String log) {
        project.println("RouterPlugin> "+log)
    }
}
