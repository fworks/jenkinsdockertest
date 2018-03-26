pipeline {
		agent { dockerfile true }
    stages {
    		stage('Example') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
            }
        }
    		stage('Build') {
            steps {
                /* Generate the jars */
                sh 'mvn clean install -Dmaven.test.skip=true'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true 
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Integration Test') {
            steps {
            		/* start services */
            		sh 'java -Djava.security.egd=file:/dev/./urandom  -jar /app.jar'
            		/* run tests */
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}