package com.travis.awesome.algorithmsandbox;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DijkstrasActivity extends Activity implements OnClickListener {
	
	//CONSTANTS
	private static final int VERTICES = 5;
	private static final int EDGES = 10;
	private static final int WEIGHT_RANGE = 40; 
	private static final boolean NEGATIVE_WEIGHTS = false; 
	private static final int SOURCE_VERTEX = 0;
	
	//UI ELEMENTS
	private Button dijkstras_button;
	private Button generate_button;
	private TextView generate_text;
	private TextView generated_graph;
	private TextView algorithm_output;
	
	//LOGIC
	private boolean graph_generated = false;
	
	//CLASS OBJECTS
	private Graph test_graph;
	
	
	public class Graph{
		
		public int vertices[];
		public Edge edges[];

		public Graph(int vertex_count, int edge_count, int weight_range, boolean negative_weights)
		{
			//NOTES: currently all weights are non zero and positive
			//I have not implemented the negate weights section
			//we assume paths are one way from vertex1 to vertex2 

			
			int temp_vertex1;
			int temp_vertex2;
			int temp_weight;

			if (vertex_count < 2 || edge_count < 0 || weight_range < 1)
				return;
			
			vertices = new int[vertex_count];
			edges = new Edge[edge_count];

			for (int i = 0; i<vertex_count; i++)
			{
				vertices[i] = i;
			}
			
			
			for (int i = 0; i<edge_count; i++)
			{
				temp_vertex1 = (int) (Math.random()*(vertex_count));
				temp_vertex2 = (int) (Math.random()*(vertex_count));
				while(temp_vertex1 == temp_vertex2)
				{
					temp_vertex2 = (int) (Math.random()*(vertex_count));
				}
				temp_weight = (int) ((Math.random()*(weight_range)) + 1);	
				
				Edge temp_edge = new Edge(temp_vertex1, temp_vertex2, temp_weight);
				edges[i] = temp_edge;				
			}
		}
	}
	public class Edge {
		
		public int vertex1;
		public int vertex2;
		public int weight;
	
		public Edge ()
		{
		}
		
		public Edge (int vertex1, int vertex2, int weight)
		{
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.weight = weight;
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dijkstras);
		
		dijkstras_button = (Button) findViewById(R.id.dijkstras_button);
		generate_button = (Button) findViewById(R.id.generate_button);
		generate_text = (TextView) findViewById(R.id.generate_text);
		generated_graph = (TextView) findViewById(R.id.generated_graph);
		algorithm_output = (TextView) findViewById(R.id.algorithm_output);
		
		dijkstras_button.setOnClickListener(this);
		generate_button.setOnClickListener(this);
	}

	
	private void GenerateGraph()
	{
		test_graph = new Graph(VERTICES, EDGES, WEIGHT_RANGE, NEGATIVE_WEIGHTS);
		if (test_graph == null)
		{
			Log.i("Dijk", "failed to generate");
			generate_text.setText(R.string.string_graph_build_failed);
		}
		else
		{
			Log.i("Dijk", "Generated correctly");
			generate_text.setText(R.string.string_graph_build_success);
			graph_generated = true;
			DisplayGraphData(test_graph);
		}
	}
	
	
	private void DisplayGraphData(Graph graph)
	{
		String text;
		text = "Vertices: " + graph.vertices.length + "\n \nEdges: \n";
		for ( int i = 0; i< graph.edges.length; i++)
		{
			text = text + "(" + graph.edges[i].vertex1 + "," + graph.edges[i].vertex2 + ") [" + graph.edges[i].weight + "]\n";
		}
		generated_graph.setText(text);
	}




	private boolean DijkstrasAlgorithm(Graph graph, int source)
	{
		
		int vertex_distances[] = new int[graph.vertices.length];
		int previous_vertices[] = new int[graph.vertices.length];
		List<Integer> finished_vertices = new ArrayList<Integer>();//stores array positions of all finished vertices
		List<Integer> unfinished_vertices = new ArrayList<Integer>();//stores array positions of all unfinished vertices

		
		for (int i = 0; i<graph.vertices.length; i++)
		{
			unfinished_vertices.add(i);//add the array position of each vertex. This allows easy referencing 			
			vertex_distances[i] = Integer.MAX_VALUE;
			previous_vertices[i] = -1;
		}

		vertex_distances[source] = 0;
		
		while(finished_vertices.size()<graph.vertices.length)
		{
			//pick the vertex, v, in unfinished_vertices with the shortest path to s
			int shortest_found_distance = vertex_distances[unfinished_vertices.get(0)];//set the value to be the vertex distance of that one
			int shortest_distance_array_position = unfinished_vertices.get(0);
			int shortest_distance_list_position = 0;

			//find closest new vertex
			for (int i = 0; i<unfinished_vertices.size(); i++)//for each unfinished vertex
			{
				if (vertex_distances[unfinished_vertices.get(i)]< shortest_found_distance) //if the current vertex distance is now the shortest
				{
					shortest_found_distance = vertex_distances[unfinished_vertices.get(i)];
					shortest_distance_array_position =  unfinished_vertices.get(i);
					shortest_distance_list_position = i;
				}
			}

			//add found shortest distance vertex to finished vertices
			finished_vertices.add(shortest_distance_array_position);
			unfinished_vertices.remove(shortest_distance_list_position);
			
			//for each edge of v, (v1, v2)			
			//iterate across all vertices looking for vertex1 to equal the current vertex id
			for (int i = 0; i< graph.edges.length; i++)
			{
				if (graph.edges[i].vertex1 == graph.vertices[shortest_distance_array_position])
				{
					if (shortest_found_distance != Integer.MAX_VALUE && (shortest_found_distance + graph.edges[i].weight< vertex_distances[graph.edges[i].vertex2]))
					{
						vertex_distances[graph.edges[i].vertex2] = shortest_found_distance + graph.edges[i].weight;
						previous_vertices[graph.edges[i].vertex2] = graph.edges[i].vertex1;						
					}
				}
			}
		}

		String text = "Results: \n";
		for (int i = 0; i<graph.vertices.length; i++)
		{
			text = text + " [" + i + "]: Distance: ";
			if (vertex_distances[i] == Integer.MAX_VALUE)
			{
				text = text + "Infinity, Prev Vert: Null \n";
			}
			else
			{
				text = text + vertex_distances[i] + " Prev Vert: " + previous_vertices[i] + "\n";
			}
		}
		algorithm_output.setText(text);
		return true;
	}
	
	
	
	@Override
	public void onClick(View view) {

		switch (view.getId()){
		case (R.id.generate_button):
			GenerateGraph();
			break;
		case (R.id.dijkstras_button):

			if (test_graph!=null)
			{
				DijkstrasAlgorithm(test_graph, SOURCE_VERTEX);
			}
			break;
		}			
	}
}
