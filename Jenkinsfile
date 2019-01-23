pipeline {
    agent any
    tools
    {
        maven '3.3.9'
        jdk 'jdk8'
    }
    environment
    {
    	 MAVEN_OPTS="-Xmx200m"
    }
    stages
    {
        stage ('Initialize')
         {
            steps
            {
                sh '''
                    mvn -B dependency:resolve

                '''
            }
        }

        stage ('Build+test')
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
        fixed
        {
            telegramSend """
            Build *${env.JOB_NAME}* #${env.BUILD_NUMBER} status:*Fixed !*, [details](${env.BUILD_URL})
            """
        }

        regression
        {
            telegramSend """
            Build *${env.JOB_NAME}* #${env.BUILD_NUMBER} status:*Regression!*, [details](${env.BUILD_URL})
            """
        }
    }
}