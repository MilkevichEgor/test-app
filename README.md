Чтобы запусить приложение необходимо: java-21, docker

1- производим сборку проекта с помощью mvn clean package или ./mvnw clean package

2- Поднять базу данных b приложение с помощью Docker
        команда для поднятия: docker compose up -d

в проекте подключены миграции и при старте приложения должны автоматически накатываться схемы и тестовые данные, 
так же подключён testcontainers, чтобы не изменять данные в основной базе, можно запускать приложение через TestContainerApplication


