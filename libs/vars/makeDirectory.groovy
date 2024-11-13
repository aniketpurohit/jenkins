/*

map directory format
=====================
{
    parent = "path/to/parent",
    children = [child0, child1, ...]
}
*/


def call(Map directory) {
    if (directory.parent) {  // Check if parent is not null or empty
        for (child in directory.children) {
            def temp = "${directory.parent}/${child}"  // Correct variable interpolation
            sh "mkdir -p ${temp}"  // Create the directory
        }
    } else {
        error "Parent directory path is not provided."
    }
}
