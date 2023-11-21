##################################################################
#          Project Convenience Makefile Wrapper for Maven        #
##################################################################

# This makefile is just a convenience wrapper for the Maven
# program. The actual building rules for this project may
# be found in the Maven "pom.xml" file located in this folder.

######################### DEFINITIONS ############################

# Define the commandline invocation of Maven if necessary:
ifeq ($(MAKE),)
    MAKE  := make
endif

ifeq ($(MFLAGS),)
    MFLAGS := -j2
endif

run-app:
	 flutter run -d chrome

build-app:
	flutter clean
	flutter build web
	flutter build apk --split-per-abi
	flutter test

clean-app:
	flutter clean

test-app:
	flutter test

# app:
# 	@ $(MAKE) $(MFLAGS) clean && \ 
# 	$(MAKE) $(MFLAGS) build

# clean:
# 	flutter clean

# build:
# 	flutter build web && \ 
# 	flutter build apk --split-per-abi