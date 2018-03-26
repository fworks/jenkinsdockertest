pipeline {
		agent { dockerfile true }
    stages {
    		stage('Build') {
            steps {
                sh 'mvn clean install -Dmaven.test.skip=true'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}