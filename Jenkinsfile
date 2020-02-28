pipeline {
    agent any

    stages {
        stage('test country and bank only stage') {
            steps {
                bat "mvn -Dmaven.test.failure.ignore=true test -DsuiteXmlFile=/src/test/resources/country.xml"
                bat "mvn -Dmaven.test.failure.ignore=true test -DsuiteXmlFile=/src/test/resources/bank.xml"
            }
        }
        stage('result stage') {
            steps {
               step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
            }

            post {
                success {
                    telegramSend "Build was successfull, link: ${BUILD_URL}"
                }
                failure {
                    telegramSend "Build was failure, link: ${BUILD_URL}"
                }
                unstable {
                    telegramSend "Build was unstable, link: ${BUILD_URL}"
                }
            }
        }
        stage('echo stage') {
            steps {
                echo "echo!"
            }
        }

    }
}