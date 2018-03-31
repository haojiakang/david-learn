# 设置从用户之前创建的sshd镜像继承。
FROM sshd:dockerfile
MAINTAINER jiakang943578 (jiakang943578@docker.com)

RUN apt-get update && \
    apt-get install -y mongodb pwgen && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 创建mongodb存放数据文件的文件夹
RUN mkdir -p /data/db
VOLUME /data/db

ENV AUTH yes

# 添加脚本
ADD run.sh /run.sh
ADD set_mongodb_password.sh /set_mongodb_password.sh
RUN chmod 755 ./*.sh

EXPOSE 27017
EXPOSE 28017

CMD ["/run.sh"]
