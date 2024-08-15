# 事件管理应用

这是一个简单的事件管理应用，允许用户管理事件。该应用包含一个基于 Spring Boot 的后端和一个基于 React 的前端。用户可以通过 Web 界面添加、修改、删除和查看事件。
当前git仅包括后端工程代码，前端代码位于：https://github.com/wuyaqi2014/incident-management-frontend
## 特性

- **事件管理**：创建、更新、删除和查看事件。
- **事件内容**：title、discription、startTime、endTime、remark
- **事件状态**：新建、处理中、已解决、已删除
- **内存存储**：数据存储在H2本地内存数据库中，没有持久存储。
- **验证和错误处理**：实现了适当的验证和错误处理机制。
- **权限校验**：有super管理员权限：admin或者system，非管理员用户只能查看自己创建的事件。

## 技术栈

- **后端**：Java, Spring Boot
- **前端**：React
- **构建工具**：Maven（用于 Java）
- **其他**：Docker（用于容器化）,k8s(用于集群部署),jmeter(用于监控)，Actuator（用于监控）

## 快速开始

### 先决条件

- Java 11 或更高版本
- Maven

### 后端设置

1. **克隆仓库：**

    ```bash
    git clone <repository-url>
    cd incident-management
    ```

2. **构建后端：**

   使用 Maven 构建 Spring Boot 后端。会在 `incident-management-api/target` 目录中生成可执行 JAR 文件。

    ```bash
    mvn clean package
    ```

3. **运行后端：**

   通过java -jar命令执行jar包，即可运行后端程序。

    ```bash
    java -jar incident-management-api/target/incident-management-api.jar
    ```

   后端将可在 `http://localhost:8090/rest/v1/incident/test` 访问。

### 前端设置
参考[README](https://github.com/wuyaqi2014/incident-management-frontend)

### 使用 Docker

你可以使用 Docker 来容器化应用。

1. **构建 Docker 镜像：**

   在后端incident-management-api目录下执行以下命令：

    ```bash
    docker build -t incident-management-api:latest .
    ```
![img_7.png](img_7.png)
![img_8.png](img_8.png)
2. **运行Docker容器：**

    ```bash
    docker run -d -p 8090:8080 incident-management-api:latest
    ```
   '-d'：在后台运行容器
   '-p 8090:8080'：将容器的 8090 端口映射到主机上的 8080 端口
3. 
   现在，后端将可在 `http://localhost:8090/rest/v1/incident/test` 访问。

### 使用 k8s
1. **将docker镜像推送到Docker hub：**

    ```bash
    docker login
    ```
   ![img.png](img.png)
2. **标记镜像：**

    ```bash
     docker tag  incident-management-api:latest your-dockerhub-username/incident-management-api:latest
    ```
![img_1.png](img_1.png)
3. **推送镜像到Docker Hub：**
    ```bash
   docker push your-dockerhub-username/incident-management-api:latest
    ```
![img_2.png](img_2.png)
![img_3.png](img_3.png)
4. **部署文件： (deployment.yaml)**
   参考目录：incident-management-api/k8s/deployment.yaml
   replicas：定义运行的应用副本数量。
   image：指定容器镜像的地址。
5. **服务文件 service.yaml**
   参考目录：incident-management-api/k8s/service.yaml
   NodePort：将服务暴露在 Kubernetes 集群外部，通过节点的 IP 访问。
6. **部署到kubernetes集群**
   ```bash
   kubectl apply -f deployment.yaml
   kubectl apply -f service.yaml
    ```
   对外暴漏30007端口，可访问：
现在，后端将可在 `http://localhost:30007/rest/v1/incident/test` 访问。
![img_4.png](img_4.png)

### 性能测试
   使用springboot actuator 提供的jmx接口进行性能测试。
   增加了spring-boot-starter-actuator依赖包，
   同时配置了actuator端点 
1. **通过curl请求，返回所有指标名称列表**
   ```bash
    curl http://localhost:8080/actuator/metrics
    ```
![img_5.png](img_5.png)

2. **通过curl请求,查看特定指标的数据:**
   ```bash
     curl http://localhost:8080/actuator/metrics/http.server.requests
    ```
返回：
 ```bash
{"name":"http.server.requests","description":null,"baseUnit":"seconds","measurements":[{"statistic":"COUNT","value":11.0},{"statistic":"TOTAL_TIME","value":0.7007925409999999},{"statistic":"MAX","value":0.011495042}],"availableTags":[{"tag":"exception","values":["None","HttpRequestMethodNotSupportedException","PlatformException"]},{"tag":"method","values":["POST","PUT","GET"]},{"tag":"uri","values":["/rest/v1/incident/incident_list_all","root","/rest/v1/incident/delete_incident/{id}","/rest/v1/incident/test","/rest/v1/incident/update_incident/{id}","/rest/v1/incident/create_incident","/actuator/metrics"]},{"tag":"outcome","values":["SUCCESS"]},{"tag":"status","values":["200"]}]}    
   ```
3. **健康检查/health端点**
   ```bash
    curl http://localhost:8080/actuator/health    
    ```
4. **通过/actuator/prometheus查看prometheus监控数据:**
   ```bash
    curl http://localhost:8080/actuator/prometheus
    ```
   ![img_6.png](img_6.png)
### 

## API 端点

后端提供了以下 RESTful API 端点：

- `GET /rest/v1/incident/incident_list_all`：列出所有事件，分页查询。
- `POST /rest/v1/incident/create_incident`：创建一个新事件。
- `PUT /rest/v1/incident/update_incident/{id}`：更新一个现有事件。
- `PUT /rest/v1/incident/delete_incident/{id}`：按 ID 删除一个事件。

### 示例 API 请求

以下是使用 `curl` 创建新事件的示例：

新建title："title1"的事件：
   ```bash
curl --location --request POST 'http://localhost:8080/rest/v1/incident/create_incident' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "title1",
    "description": "description1",
    "startTime": 1723651200000,
    "endTime": 1723737600000,
    "remark": "remark"
}'
   ```
首次新建返回：
   ```bash
  {
    "result": 1,
    "errorMsg": "成功",
    "data": {
        "incidentId": 2,
        "success": true
    },
    "success": true
}
   ```
再次新建title："title1"的事件，会提示相同该用户下title不能重复：
   ```bash
curl --location --request POST 'http://localhost:8080/rest/v1/incident/create_incident' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "title1",
    "description": "description1",
    "startTime": 1723651200000,
    "endTime": 1723737600000,
    "remark": "remark"
}'
   ```
   ```bash
  {
    "result": 2,
    "errorMsg": "同一用户下，title不能重复",
    "data": null,
    "success": false
  }
   ```
查询事件列表：支持分页查询，从第一页开始。
   ```bash
curl --location --request POST 'http://localhost:8080/rest/v1/incident/incident_list_all' \
--header 'Content-Type: application/json' \
--data-raw '{
    "page": 1,
    "pageSize":4
}'
   ```
返回：
   ```bash
{
    "result": 1,
    "errorMsg": "成功",
    "data": {
        "data": [
            {
                "id": 1,
                "title": "title1",
                "description": "description1",
                "status": 1,
                "startTime": 1723651200000,
                "endTime": 1723737600000,
                "remark": "remark",
                "createdBy": "wuyaqi",
                "updatedBy": "wuyaqi",
                "createdTime": 1723700056304,
                "updatedTime": 1723700056304
            }
        ],
        "currentPage": 1,
        "pageSize": 4,
        "totalCount": 1
    },
    "success": true
}
   ```
编辑事件
   ```bash
curl --location --request PUT 'http://localhost:8080/rest/v1/incident/update_incident/1' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "title2",
"description": "description1",
"startTime": 1723651200000,
"endTime": 1723737600000,
"remark": "title2"
}'
   ```
编辑事件返回：
   ```bash
   {
    "result": 1,
    "errorMsg": "成功",
    "data": {
        "incidentId": 1,
        "success": true
    },
    "success": true
}
   ```

再次查询列表返回如下：title和remark字段有变更
   ```bash
   {
    "result": 1,
    "errorMsg": "成功",
    "data": {
        "data": [
            {
                "id": 1,
                "title": "title2",
                "description": "description1",
                "status": 1,
                "startTime": 1723651200000,
                "endTime": 1723737600000,
                "remark": "title2",
                "createdBy": "wuyaqi",
                "updatedBy": "wuyaqi",
                "createdTime": 1723700056304,
                "updatedTime": 1723700250509
            }
        ],
        "currentPage": 1,
        "pageSize": 4,
        "totalCount": 1
    },
    "success": true
}
   ```
删除事件：
   ```bash
curl --location --request PUT 'http://localhost:8080/rest/v1/incident/delete_incident/1' \
--header 'Content-Type: application/json' \
--data-raw ''
   ```
删除事件返回：
   ```bash
{
    "result": 1,
    "errorMsg": "成功",
    "data": true,
    "success": true
}
   ```
再次查询列表返回结果为空
   ```bash
{
    "result": 1,
    "errorMsg": "成功",
    "data": {
        "data": null,
        "currentPage": 1,
        "pageSize": 4,
        "totalCount": 0
    },
    "success": true
}
   ```

以上是该项目的所有后端工作，由于时间原因，还有部分功能待优化：
1、功能相关：
   （1）、添加用户管理功能，目前只支持单用户，后续会添加用户管理功能，支持多用户。
   （2）、添加事件状态管理功能，目前只支持创建事件，后续会添加事件状态管理功能，根据事件的startTime和endTime进行异步状态扭转。
   （3）、添加事件查询功能，目前只支持分页查询，后续会添加事件查询功能，支持根据事件ID、title、description、startTime、endTime、status
2、权限相关：
   （1）、由于没有登录功能，目前所有操作的用户都是"wuyaqi", 查询、编辑都是只能查到操作人为"wuyaqi"的所有事件。
3、单元测试：
   （1）、添加单元测试，目前只支持手动测试，单元测试用例较少，后续会添加单元测试，支持自动化测试
4、多线程、高并发：
   （1）、由于现在后端功能较为简单，暂未考虑多线程、高并发等场景，后续如果有需要可增加
5、缓存: 
   （1）、现有的功能：新建、编辑、删除，都需要增加缓存。
   （2）、查询list，可以增加缓存，但是目前是分页查询，不适合增加缓存。
   （3）、像页面上的操作的功能，不适合增加缓存，如果增加缓存，需要增加刷新频率，否则就会出现页面不一致的情况。影响使用。
   （4）、对外提供的rpc查询接口，比如查全量事件接口，可以适当增加缓存，减少sql的压力。
6、日志：
   （1）、增加traceId,后续方便查问题。