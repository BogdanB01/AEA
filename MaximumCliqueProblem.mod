/*********************************************
 * OPL 12.8.0.0 Model
 * Author: BogdanBo
 * Creation Date: 24 oct. 2019 at 13:10:45
 *********************************************/

int nbVertex = ...; 
 
range Vertex = 1..nbVertex;
 
tuple Edge {
    key int point;
    {int} neighbors; 
};

{Edge} graph = ...;

Edge getVertex[Vertex] = [e.point : e | e in graph];
 
dvar boolean zVar [Vertex];

dexpr int objective= sum(v in Vertex)  zVar[v];

maximize sum(v in Vertex)  zVar[v];

subject to {

	forall (v1 in Vertex, v2 in (v1 + 1)..nbVertex: !(v2 in getVertex[v1].neighbors)) {
	         zVar[v1] + zVar[v2] <= 1;        
	}

}

execute PRINT_SOLUTION {
    var txt = "maxClique cardinal "
    txt += objective + "\nNoduri "
    for (var v in Vertex)
       if(zVar [v]==1)  txt += v + " "
    writeln(txt)
}