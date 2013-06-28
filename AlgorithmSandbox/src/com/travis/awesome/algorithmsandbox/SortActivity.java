package com.travis.awesome.algorithmsandbox;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SortActivity extends Activity implements OnClickListener {

	private static final int ARRAY_SIZE = 50;
	private static final int SHUFFLE_AMOUNT = 50;
	
	
	private Button shuffle_button;
	private Button sort_brute_force_button;
	private Button sort_bubble_button;
	private Button sort_merge_button;
	private TextView shuffle_text;
	private TextView algorithm_output;
	
	int sample_array[] = new int[ARRAY_SIZE];
	
	//LOGIC
	private boolean array_sorted = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort);
	
		InitializeLayout();
		GenerateArray(sample_array);
	}
	
	private void InitializeLayout()
	{
	
		shuffle_button = (Button) findViewById(R.id.shuffle_button);
		sort_brute_force_button = (Button) findViewById(R.id.sort_brute_force_button);
		sort_bubble_button = (Button) findViewById(R.id.sort_bubble_button);
		sort_merge_button = (Button) findViewById(R.id.sort_merge_button);
		shuffle_text = (TextView) findViewById(R.id.shuffle_text);
		algorithm_output = (TextView) findViewById(R.id.algorithm_output);
		shuffle_button.setOnClickListener(this);
		sort_brute_force_button.setOnClickListener(this);
		sort_bubble_button.setOnClickListener(this);
		sort_merge_button.setOnClickListener(this);
	}
	
	private void GenerateArray(int array[])
	{
		for (int i = 0; i< array.length; i++)
		{
			array[i] = i;
		}
	}
	
	private void ShuffleArray(int array[], int shuffle_amount, int array_size)
	{
		int position1;
		int position2;
		int shuffle_count = 0;
		
		while(shuffle_count<shuffle_amount)
		{
			position1 = (int) (Math.random()*array_size);
			position2 = (int) (Math.random()*array_size);
			
			while (position1 == position2)
			{
				position2 = (int) (Math.random()*array_size);
			}
			
			//swap positions
			SwapEntries(array, position1, position2);
			shuffle_count++;
		}
		
		array_sorted = false;
	}
	
	private void BruteForceSort(int array[])//Selection Sort
	{
		
		int lowest_found;
		int lowest_found_position;
		
		
		for (int i = 0; i<array.length; i++)
		{
			lowest_found = array[i];
			lowest_found_position = i;

			for (int j = i; j<array.length; j++)
			{
				if (array[j]<lowest_found)
				{
					lowest_found = array[j];
					lowest_found_position = j;
				}
			}
			SwapEntries(array, lowest_found_position, i);
		}
	}
	
	private void BubbleSort(int array[]) 
	{
		boolean finished_sorting = false;
		int countdown = 0;
		
		while (!finished_sorting)
		{
			finished_sorting = true;

			for (int i = 0; i<array.length - 1 - countdown; i++)
			{
				if (array[i] > array[i+1])
				{
					SwapEntries(array, i, i+1);
					finished_sorting = false;
				}
			}
			countdown += 1;
		}
	}
	
	private List<Integer> MergeSort(List<Integer> list)
	{
		if (list.size() <=1)
		{
			return list;
		}

		List<Integer> left = new ArrayList<Integer>();
		List<Integer> right =  new ArrayList<Integer>();
		int middle = list.size()/2;
		
		for (int i = 0; i<list.size(); i++)
		{
			if (i<middle)
			{
				left.add(list.get(i));
			}
			else
			{
				right.add(list.get(i));
			}
		}
		left = MergeSort(left);
		right = MergeSort(right);

		return Merge(left, right);
	}

	private List<Integer> Merge(List<Integer>  left, List<Integer> right)
	{
		List<Integer> result = new ArrayList<Integer>();

		while(left.size()>0 || right.size()>0)
		{
			if (left.size()>0 && right.size()> 0)
			{
				if (left.get(0) <=right.get(0))
				{
					result.add(left.get(0));
					left.remove(0);
				}
				else
				{
					result.add(right.get(0));
					right.remove(0);
				}
			}
			else if (right.size()>0)
			{
				result.add(right.get(0));
				right.remove(0);
			}
			else if (left.size()>0)
			{
				result.add(left.get(0));
				left.remove(0);
			}
		}
		return result;
	}
		
	
	private void SwapEntries(int array[], int position1, int position2)
	{
		if (position1 != position2)
		{
			int temp1;
			temp1 = array[position2];
			array[position2] = array[position1];
			array[position1] = temp1;
		}
	}
	
	private List<Integer> ArrayToList(int array[])
	{
		List<Integer> results = new ArrayList<Integer>();
		
		for (int i = 0; i<array.length; i++)
		{
			results.add(array[i]);
		}
		return results;
	}

	private int[] ListToArray(List<Integer> list)
	{
		int results[] = new int[list.size()];
		
		for (int i = 0; i<list.size(); i++)
		{
			results[i] = list.get(i);
		}

		return results;
	}

	private void DisplayArray(int array[])
	{
		if (array.length>50 && array[0] == 0)
		{
			algorithm_output.setText("Sorted");
			return;
		}
		else if (array.length>50 && array[0] != 0)
		{
			algorithm_output.setText("Shuffled");
			return;
		}
		String text = "(";
		
		for (int i = 0; i<array.length; i++)
		{
			if (i+2>array.length)
			{
				text = text + " " + array[i];
			}
			else
			{
				text = text + " " + array[i] + ",";
			}
		}
		text = text + ")";
		algorithm_output.setText(text);
		
	}
	
	@Override
	public void onClick(View view) {

		switch (view.getId()){
		case (R.id.shuffle_button):
			
			ShuffleArray(sample_array, SHUFFLE_AMOUNT,ARRAY_SIZE);
			DisplayArray(sample_array);
			shuffle_text.setText(R.string.string_shuffled);
			break;
		case (R.id.sort_brute_force_button):
			if (!array_sorted)
			{
				BruteForceSort(sample_array);
				array_sorted = true;		
				DisplayArray(sample_array);
				shuffle_text.setText(R.string.string_sorted);
			}
		case (R.id.sort_bubble_button):
			if (!array_sorted)
			{
				BubbleSort(sample_array);
				array_sorted = true;		
				DisplayArray(sample_array);
				shuffle_text.setText(R.string.string_sorted);
			}
		case (R.id.sort_merge_button):
			if (!array_sorted)
			{
				
				sample_array = ListToArray(MergeSort(ArrayToList(sample_array)));
				array_sorted = true;		
				DisplayArray(sample_array);
				shuffle_text.setText(R.string.string_sorted);
			}
		
		
			break;
		}			
	}
}
