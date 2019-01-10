pipeline {
    agent any
    tools
    {
        maven '3.6.0'
        jdk 'jdk8'
    }
    stages
    {
        stage ('Initialize')
         {
            steps
            {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build')
        {
            steps
            {
                sh 'mvn -B -Dmaven.test.failure.ignore=true test' 
            }
            post
            {
                always
                {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
    }
    post
    {
        always
        {
            emailext (
                subject: "[jenkins][${env.JOB_NAME}] Build ${env.BUILD_NUMBER} status:${currentBuild.currentResult}",
                mimeType: 'text/html',
                body: '${JELLY_SCRIPT,template="html"}',
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }


        fixed
        {
            emailext (
                subject: "[jenkins][${env.JOB_NAME}] Build ${env.BUILD_NUMBER} fixed",
                mimeType: 'text/html',
                body: '${JELLY_SCRIPT,template="html"}',
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        regression
        {
            emailext (
                subject: "[jenkins][${env.JOB_NAME}] Build ${env.BUILD_NUMBER} regression",
                mimeType: 'text/html',
                body: '${JELLY_SCRIPT,template="html"}',
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
    }
}