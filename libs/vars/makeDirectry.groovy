/*

map directory format
=====================
{
    parent = "path/to/parent",
    children = [child0, child1, ...]
}
*/


def call(map directory, String parent= ''){
    if (directory.parent != null) {
        for(child in directory.children) {
            def temp = "${directory.parent}/child"
            sh "mkdir -p ${temp}"
        }
    }
    
}