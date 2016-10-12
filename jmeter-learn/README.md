1.在david-learn目录下，运行mvn clean package -Dmaven.test.skip=true -pl jmeter-learn -am命令打包
  jmeter-learn的pom.xml里面必须有
      <build>
          <plugins>
              <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-dependency-plugin</artifactId>
                  <executions>
                      <execution>
                          <id>copy-dependencies</id>
                          <phase>prepare-package</phase>
                          <goals>
                              <goal>copy-dependencies</goal>
                          </goals>
                          <configuration>
                              <outputDirectory>${project.build.directory}/lib</outputDirectory>
                              <overWriteReleases>false</overWriteReleases>
                              <overWriteSnapshots>false</overWriteSnapshots>
                              <overWriteIfNewer>true</overWriteIfNewer>
                          </configuration>
                      </execution>
                  </executions>
              </plugin>
              <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-jar-plugin</artifactId>
                  <configuration>
                      <archive>
                          <manifest>
                              <addClasspath>true</addClasspath>
                              <classpathPrefix>lib/</classpathPrefix>
                              <!--<mainClass>theMainClass</mainClass>-->
                          </manifest>
                      </archive>
                  </configuration>
              </plugin>
          </plugins>
      </build>
  才能够把依赖的第三方jar包同时打包到target/lib目录下
2.使用cp workspace/david-learn/jmeter-learn/target/jmeter-learn-1.0-SNAPSHOT.jar develop/apache-jmeter-3.0/lib/ext/将jar报复制到jmeter的lib/ext下
  使用cp workspace/david-learn/jmeter-learn/target/lib/*.jar develop/apache-jmeter-3.0/lib/ext/将jar报复制到jmeter的lib/ext/下
3.运行develop/apache-jmeter-3.0/bin/jmeter.sh打开jmeter客户端
4.add->Threads->Thread Group添加一个Thread Group
5.Thread Group->add->Sampler->Java Request添加一个Java Request
  Thread Group->add->Listener->Aggregate Report添加一个监控
6.点击Java Request选择TestLength类
7.运行