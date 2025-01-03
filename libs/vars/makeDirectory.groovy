/*

map directory format
=====================
{
    parent = "path/to/parent",
    children = [child0, child1, ...]
}
*/

@Library('shared-lib') _

def call(Map directory) {
    if (directory.parent) {  // Check if parent is not null or empty
        for (child in directory.children) {
            def temp = "${directory.parent}/${child}"  // Correct variable interpolation
            sh "mkdir -p ${temp}"  // Create the directory
        }
    } 
    else if(directory.children){
        for(child in directory.children){
        sh "mkdir -p ${child}"
        }
    }
    else {
        error "Parent directory path is not provided."
    }
}
