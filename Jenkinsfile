pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'Java'
    }
    parameters {

            choice(name: "packageType", choices: ["WebTest.java", "APITest.java", "DbTest.java"], description: "Sample multi-choice parameter")
        }
    stages {
        stage('build') {
            steps {
                echo %packageType%
                bat 'mvn clean test -Dtest=${params.packageType}'
            }
        post {                
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success { allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
                ])
            }
    }
        }
}
}
/