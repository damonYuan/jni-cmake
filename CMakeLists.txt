cmake_minimum_required(VERSION 3.23)
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED True)
set(CMAKE_SYSTEM_NAME Darwin)
set(MAIN_APP_PROJECT_NAME sbmcprj)
set(PROJECT_LIB_NAME sbmc)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)
if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif()
message (STATUS "CMAKE_CURRENT_SOURCE_DIR=${CMAKE_CURRENT_SOURCE_DIR}")
message (STATUS "CMAKE_CURRENT_BINARY_DIR=${CMAKE_CURRENT_BINARY_DIR}")
message (STATUS "Java_JAVAC_EXECUTABLE=${Java_JAVAC_EXECUTABLE}")
include(UseJava)

project(${MAIN_APP_PROJECT_NAME})

# compile xxx.java to class file
set(CMAKE_JAVA_COMPILE_FLAGS "-source" "11" "-target" "11")
add_jar(${MAIN_APP_PROJECT_NAME}
    src/main/java/com/damonyuan/jni/HelloWorldJNI.java
    ENTRY_POINT com/damonyuan/jni/HelloWorldJNI)
get_target_property(_jarFile ${MAIN_APP_PROJECT_NAME} JAR_FILE)
get_target_property(_classDir ${MAIN_APP_PROJECT_NAME} CLASSDIR)

# generate xxx.h stub
set (_stubDir "${CMAKE_CURRENT_BINARY_DIR}")
add_custom_command(
    OUTPUT Java_com_damonyuan_jni_HelloWorldJNI.h
    COMMAND ${Java_JAVAC_EXECUTABLE} -h
        -classpath ${_classDir}
        -d ${_stubDir}
        -jni ${MAIN_APP_PROJECT_NAME}
    DEPENDS ${MAIN_APP_PROJECT_NAME}
    )

# generate libsbmc.dylib
include_directories(${PROJECT_LIB_NAME}
    ${JNI_INCLUDE_DIRS}
    ${_classDir}
    ${_stubDir}
    $ENV{JAVA_HOME}/include/darwin
    )
add_library(${PROJECT_LIB_NAME} MODULE
    native/com_damonyuan_jni_HelloWorldJNI.cpp
    native/com_damonyuan_jni_HelloWorldJNI.h)
set_target_properties(${PROJECT_LIB_NAME} PROPERTIES SUFFIX ".dylib")
target_link_libraries(${PROJECT_LIB_NAME} ${JNI_LIBRARIES})

message(STATUS "Jar file ${_jarFile}")
message(STATUS "Class compiled to ${_classDir}")