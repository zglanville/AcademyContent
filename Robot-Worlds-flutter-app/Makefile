##################################################################
#          Project Convenience Makefile Wrapper for Maven        #
##################################################################

# This makefile is just a convenience wrapper for the Maven
# program. The actual building rules for this project may
# be found in the Maven "pom.xml" file located in this folder.

######################### DEFINITIONS ############################

# Define the commandline invocation of Maven if necessary:
ifeq ($(MVN),)
    MVN  := mvn
endif

ifeq ($(GIT),)
    GIT  := git
endif

ifeq ($(MAKE),)
    MAKE  := make
endif

ifeq ($(MFLAGS),)
    MFLAGS := -j2
endif


ifeq ($(RELEASE_VERSION),)
    RELEASE_VERSION  := $(shell xmllint --xpath "/*[local-name() = 'project']/*[local-name() = 'version']/text()" pom.xml | perl -pe 's/-SNAPSHOT//')
endif

ifeq ($(NEXT_VERSION),)
    NEXT_VERSION  := $(shell echo $(RELEASE_VERSION) | perl -pe 's{^(([0-9]\.)+)?([0-9]+)$$}{$$1 . ($$3 + 1)}e')
endif

ifneq (,$(findstring -SNAPSHOT,$(RELEASE_VERSION)))
	RELEASE_VERSION_NSNP = $(shell echo $(RELEASE_VERSION) | perl -pe 's/-SNAPSHOT//')
else
	RELEASE_VERSION_NSNP = $(RELEASE_VERSION)
endif

ifeq (,$(findstring -SNAPSHOT,$(NEXT_VERSION)))
	NEXT_VERSION_SNP = $(NEXT_VERSION)-SNAPSHOT
else
	NEXT_VERSION_SNP = $(NEXT_VERSION)
endif
######################## BUILD TARGETS ###########################

.PHONY: all package compile check test doc docs javadoc clean help

build-app:
	$(MAKE) -C ./RobotWorldsApp build-app

clean-app:
	$(MAKE) -C ./RobotWorldsApp clean-app

test-app:
	$(MAKE) -C ./RobotWorldsApp test-app

run-app:
	$(MAKE) -C ./RobotWorldsApp run-app

docker-build:
	sudo docker build -t gitlab.wethinkcode.de:5050/sgerber/shenzou-robot .

docker-push:
	sudo docker push gitlab.wethinkcode.de:5050/sgerber/shenzou-robot

docker-test:
	sudo docker build -t robot-worlds:test .
	sudo docker run -p 5000:5000 -p 5110:5110 -p 5210:5210 -p 5220:5220 -p 5221:5221 --name robot_worlds_test_container robot-worlds:test & > /dev/null 2>&1
	mvn -Dtest=LaunchRobotTests,MoveRobotTests,RobotStateTests test
	sudo docker stop robot_worlds_test_container
	sudo docker rm robot_worlds_test_container
	sudo docker system prune -f
	clean

docker-clean:
	sudo docker stop robot_worlds_test_container
	sudo docker rm robot_worlds_test_container
	sudo docker system prune -f

run-server:
	sudo docker build -t robot-worlds:server .
	sudo docker run -p 5000:5000 --name robot_worlds_server_container robot-worlds:server & > /dev/null 2>&1

stop-server:
	sudo docker stop robot_worlds_server_container
	sudo docker rm robot_worlds_server_container
	sudo docker system prune -f

docker-run:
	sudo docker build -t robot-worlds:test .
	sudo docker run -p 5000:5000 --name robot_worlds_test_container robot-worlds:test

package-notest:
	@ $(MVN) $(MVNFLAGS) package -Dmaven.test.skip

start-reference-servers:
	java -jar lib/reference-server-0.1.0.jar -p 5000 &
	java -jar lib/reference-server-0.1.0.jar -p 5110 -s 1 &
	java -jar lib/reference-server-0.2.3.jar -p 5210 -s 1 &
	java -jar lib/reference-server-0.2.3.jar -p 5220 -s 2 &
	java -jar lib/reference-server-0.2.3.jar -p 5221 -s 2 -o 0,1 &

start-servers:
	java -jar ServerSideApplication/target/ServerSideApplication-1.0-SNAPSHOT.jar -p 5000 -s 1 &
	sleep 0.5
	java -jar ServerSideApplication/target/ServerSideApplication-1.0-SNAPSHOT.jar -p 5110 -s 1 &
	sleep 0.5
	java -jar ServerSideApplication/target/ServerSideApplication-1.0-SNAPSHOT.jar -p 5210 -s 1 &
	sleep 0.5
	java -jar ServerSideApplication/target/ServerSideApplication-1.0-SNAPSHOT.jar -p 5220 -s 2 &
	sleep 0.5
	java -jar ServerSideApplication/target/ServerSideApplication-1.0-SNAPSHOT.jar -p 5221 -s 2 -o 0,1 &

kill-servers:
	lsof -t -i:5000 | xargs kill -9 >/dev/null 2>/dev/null &
	lsof -t -i:5110 | xargs kill -9 >/dev/null 2>/dev/null &
	lsof -t -i:5210 | xargs kill -9 >/dev/null 2>/dev/null &
	lsof -t -i:5220 | xargs kill -9 >/dev/null 2>/dev/null &
	lsof -t -i:5221 | xargs kill -9 >/dev/null 2>/dev/null &

test-server:
	@ $(MAKE) $(MFLAGS) kill-servers &
	@ $(MAKE) $(MFLAGS) start-servers >/dev/null 2>/dev/null &
	@ $(MVN) test 
	@ $(MAKE) $(MFLAGS) kill-servers &

test-reference-server:
	@ $(MAKE) $(MFLAGS) kill-servers &
	@ $(MAKE) $(MFLAGS) start-reference-servers >/dev/null 2>/dev/null &
	@ $(MVN) test
	@ $(MAKE) $(MFLAGS) kill-servers &

test-acceptance-reference:
	@ $(MAKE) $(MFLAGS) kill-servers &
	@ $(MAKE) $(MFLAGS) start-reference-servers >/dev/null 2>/dev/null &
	# @ $(MVN) -Dtest=LaunchRobotTests,MoveRobotTests,RobotStateTests,LaunchTests2x2 test
	@ $(MVN) -Dtest=LaunchRobotTests,LookRobotTests,MoveRobotTests,RobotStateTests test
	@ $(MAKE) $(MFLAGS) kill-servers &

test-acceptance:
	@ $(MAKE) $(MFLAGS) kill-servers &
	@ $(MAKE) $(MFLAGS) start-servers >/dev/null 2>/dev/null &
	@ $(MVN) -Dtest=LaunchRobotTests,MoveRobotTests,RobotStateTests,LaunchTests2x2 test
	# @ $(MVN) -Dtest=LaunchRobotTests,LookRobotTests,MoveRobotTests,RobotStateTests,LaunchTests2x2 test
	@ $(MAKE) $(MFLAGS) kill-servers &

all: 
	@ $(MVN) $(MVNFLAGS) package

clean:
	@ $(MVN) $(MVNFLAGS) clean

compile: 
	@ $(MVN) $(MVNFLAGS) compile

test:
	@ $(MVN) $(MVNFLAGS) test

qulice:	
	@ $(MVN) clean install -Pqulice

install:
	@ $(MVN) clean install
				
site:
	@ $(MVN) site -Psite
	
gh-pages:	
	@ $(MVN) clean test install site-deploy -Pgh-pages

doc:
	@ $(MVN) $(MVNFLAGS) javadoc:javadoc
		
package: 
	@ $(MVN) $(MVNFLAGS) package

deploy-staging:
	@ $(MVN) clean deploy

release-prepare:
	@ $(MVN) release:clean release:prepare

release-perform:
	@ $(MVN) release:perform -Prelease-sign

release-rollback:
	@ $(MVN) release:rollback
			
release-all:
	@ $(MVN) release:clean release:prepare release:perform -Prelease-sign

release-silent:
	@ $(MVN) -B release:clean release:prepare release:perform -Prelease-sign

manual-release-nodeploy: version-release git-checkin-release git-tag-release version-bump git-checkin-next
	
manual-release: version-release git-checkin-release nexus-deploy git-tag-release version-bump git-checkin-next

version-bump:
	@echo setting next version: $(NEXT_VERSION_SNP)
	@ $(MVN) versions:set -DgenerateBackupPoms=false -DnewVersion=$(NEXT_VERSION_SNP)

version-release:
	@echo setting release version: $(RELEASE_VERSION_NSNP)
	@ $(MVN) versions:set -DgenerateBackupPoms=false -DnewVersion=$(RELEASE_VERSION_NSNP)
	
nexus-deploy:
	@echo deploying
	@ $(MVN) -Pnexus-release -Prelease-sign clean verify source:jar javadoc:jar gpg:sign deploy

git-checkin-release:
	@ $(MVN) scm:checkin -Dmessage="preparing release - ${RELEASE_VERSION_NSNP}"
	
git-tag-release:
	@ $(MVN) scm:tag -Dtag="v${RELEASE_VERSION_NSNP}"	

git-checkin-next:
	@ $(MVN) scm:checkin -Dmessage="preparing next version - ${NEXT_VERSION_SNP}"

						
#clean:
#	@- rm -rf ./bin/*
#	@- rm -rf ./build/*
#	@- rm -rf ./docs/*

update-versions:
	@ $(MVN) versions:update-properties
	
distclean: clean ;

docs: doc ;

javadoc: doc ;

documentation: doc ;

help:
	@ echo "Usage   :  make <target>"
	@ echo "Targets :"
	@ echo "   all ........... Builds the project"
	@ echo "   clean ......... Removes build products"	
	@ echo "   compile ....... Compiles all Java files"	
	@ echo "   test .......... Builds and runs all unit tests"
	@ echo "   qulice ....... Builds and runs various static code analysis tools"	
	@ echo "   install .......... Builds and installs to local repository"	
	@ echo "   docs .......... Generates project documentation."
	@ echo "   deploy-staging .......... Deploys snapshot to staging"
	@ echo "   release .......... Releases to maven central (interactive)"
	@ echo "   release-silent .......... Releases to maven central (non-interactive)"		
	@ echo "   help .......... Prints this help message"

