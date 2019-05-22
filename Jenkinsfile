#!/usr/bin/groovy
node {
    final String cmdGitCheckout = "git clone git@github.com:denniscourneyea/rest-soap-template.git ."
    final String cmdMvnBuild = "mvn -B jgitflow:release-start jgitflow:release-finish"
    final String cmdGitMaster = "git checkout master"
    final String cmdGitPushAll = "git push --all"
    final String cmdGitPushTags = "git push --tags"

    stage("Checkout") {
        cleanWs()
        if (isUnix()) {
            sh cmdGitCheckout
        } else {
            bat cmdGitCheckout
        }
    }
    stage("Build") {
        if (isUnix()) {
            sh cmdMvnBuild
            sh cmdGitMaster
        } else {
            bat cmdMvnBuild
            bat cmdGitMaster
        }

        pom = readMavenPom file: 'pom.xml'
        baseArtifactName = pom.properties.getProperty("baseArtifactName")
        dockerImage = docker.build("localhost:8083/${baseArtifactName}:${pom.version}", "service/")

        stash name: "parent", includes: "pom.xml"
        stash name: "lib", includes: 'lib/pom.xml'
        stash name: "jaxb", includes: 'lib/jaxb/target/' + baseArtifactName + '-jaxb-' + pom.version + '.jar'
        stash name: "api", includes: 'api/target/' + baseArtifactName + '-api-' + pom.version + '.jar'
        stash name: "service", includes: 'service/target/' + baseArtifactName + '-service-' + pom.version + '.war'
    }
    stage("Test") {
        // Integration tests
    }
    stage("Publish") {
        parallel(
                "git": {
                    // Must execute on same node as build stage
                    if (isUnix()) {
                        sh cmdGitPushAll
                        sh cmdGitPushTags
                    } else {
                        bat cmdGitPushAll
                        bat cmdGitPushTags
                    }
                },
                "maven": {
                    node {
                        unstash "parent"
                        unstash "lib"
                        unstash "jaxb"
                        unstash "api"
                        unstash "service"
                        nexusArtifactUploader(
                                nexusVersion: 'nexus3',
                                protocol: 'http',
                                nexusUrl: 'localhost:8081',
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: 'maven-releases',
                                credentialsId: 'nexus',
                                artifacts: [
                                        [artifactId: 'parent',
                                         file: 'pom.xml',
                                         type: 'pom'],
                                        [artifactId: 'lib',
                                         file: 'lib/pom.xml',
                                         type: 'pom'],
                                        [artifactId: 'jaxb',
                                         file: 'lib/jaxb/target/' + baseArtifactName + '-jaxb-' + pom.version + '.jar',
                                         type: 'jar'],
                                        [artifactId: 'api',
                                         file: 'api/target/' + baseArtifactName + '-api-' + pom.version + '.jar',
                                         type: 'jar'],
                                        [artifactId: 'service',
                                         file: 'service/target/' + baseArtifactName + '-service-' + pom.version + '.war',
                                         type: 'war']
                                ]
                        )
                    }
                },
                "docker": {
                    node {
                        dockerImage.push()
                        dockerImage.push("latest")
                    }
                }
        )
    }
    stage("Deploy") {
        // Deploy to development test environment
    }
}
