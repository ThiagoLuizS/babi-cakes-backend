# babi-cakes-backend
API Backend solução e disponibilização de recursos para micros plataformas


pip3 install awscli 
- aws configure set aws_access_key_id "xxxxxx" 
- aws configure set aws_secret_access_key "xxxxxx" 
- eval $(aws ecr get-login --no-include-email --region us-east-1 | sed 's;https://;;g') -./gradlew build -x test 
- docker build -f src/main/java/br/com/sgi/config/docker/Dockerfile -t xxxxxx/sgi:0.0.1-SNAPSHOT . 
- docker push xxxxxx/sgi:0.0.1-SNAPSHOT docker pull xxxxxx/sgi:0.0.1-SNAPSHOT
- docker run -d -p 8080:9090 -e "SPRING_PROFILES_ACTIVE=test"