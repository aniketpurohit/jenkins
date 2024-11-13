// greetUser.groovy
def call(String name) {
    echo "Hello, ${name}! Welcome to the Jenkins pipeline."
    if(false) {
        echo "Some thing is false"
    }
    else if(true){
        echo "In the else if block"
    }
    else{
        echo "In the else block"
    }
}
