# 选择基础镜像
FROM openjdk:11-jre-slim

# 添加一个维护者标签（可选）
LABEL maintainer="719296501@qq.com"

# 设置工作目录
WORKDIR /app

# 将构建的 JAR 文件复制到容器中
COPY target/incident-management-api.jar incident-management-api.jar

# 暴露应用在容器内的端口
EXPOSE 9100

# 运行 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "incident-management-api.jar"]