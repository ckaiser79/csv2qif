
Needed Repsoitories for build:

                        <pluginRepositories>
                                <!-- maven2 layout -->
                                <pluginRepository>
                                        <id>central</id>
                                        <url>http://repo1.maven.org/maven2/</url>
                                        <layout>default</layout>
                                        <releases>
                                                <enabled>true</enabled>
                                                <updatePolicy>always</updatePolicy>
                                                <checksumPolicy>warn</checksumPolicy>
                                        </releases>
                                        <snapshots>
                                                <enabled>false</enabled>
                                                <updatePolicy>never</updatePolicy>
                                                <checksumPolicy>fail</checksumPolicy>
                                        </snapshots>
                                </pluginRepository>

                                <pluginRepository>
                                        <id>ibiblio</id>                                                                                         
                                        <url>http://www.ibiblio.org/maven2/</url>
                                        <layout>default</layout>
                                        <releases>
                                                <enabled>true</enabled>
                                                <updatePolicy>always</updatePolicy>
                                                <checksumPolicy>warn</checksumPolicy>
                                        </releases>
                                        <snapshots>
                                                <enabled>false</enabled>
                                                <updatePolicy>never</updatePolicy>
                                                <checksumPolicy>fail</checksumPolicy>
                                        </snapshots>
                                </pluginRepository>
                        </pluginRepositories>
                        <repositories>
                                <!-- maven2 layout-->
                                <repository>
                                        <id>central</id>
                                        <url>http://repo1.maven.org/maven2/</url>
                                        <layout>default</layout>
                                        <releases>
                                                <enabled>true</enabled>
                                                <updatePolicy>always</updatePolicy>
                                                <checksumPolicy>warn</checksumPolicy>
                                        </releases>
                                        <snapshots>
                                                <enabled>false</enabled>
                                                <updatePolicy>never</updatePolicy>
                                                <checksumPolicy>fail</checksumPolicy>
                                        </snapshots>
                                </repository>

                                <repository>
                                        <id>ibiblio</id>
                                        <url>http://www.ibiblio.org/maven2/</url>
                                        <layout>default</layout>
                                        <releases>
                                                <enabled>true</enabled>
                                                <updatePolicy>always</updatePolicy>
                                                <checksumPolicy>warn</checksumPolicy>
                                        </releases>
                                        <snapshots>
                                                <enabled>false</enabled>
                                                <updatePolicy>never</updatePolicy>
                                                <checksumPolicy>fail</checksumPolicy>
                                        </snapshots>
                                </repository>

                                <repository>
                                        <id>codehaus</id>
                                        <url>http://repository.codehaus.org/</url>
                                        <layout>default</layout>
                                        <releases>
                                                <enabled>true</enabled>
                                                <updatePolicy>always</updatePolicy>
                                                <checksumPolicy>warn</checksumPolicy>
                                        </releases>
                                        <snapshots>
                                                <enabled>false</enabled>
                                                <updatePolicy>never</updatePolicy>
                                                <checksumPolicy>fail</checksumPolicy>
                                        </snapshots>
                                </repository>

                                <!-- maven 1 layout -->
                                <repository>
                                        <id>java.net</id>
                                        <url>http://download.java.net/maven/1</url>
                                        <layout>legacy</layout>
                                </repository>
                        </repositories>

