package test;

import static test.Common.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import org.ahocorasick.trie.Trie;

import com.util.Utility;


/**
 * @author hankcs
 */
public class DetectFaultForDelete {

	void initialize(Collection<String> dictionary) throws IOException {
		dictionaryMap = new TreeMap<String, String>();
		for (String word : dictionary) {
			dictionaryMap.put(word, String.format("[%s]", word));
		}
		dictionaryMap.remove(wordsToBeDeleted);
		System.out.println("dictionary.size() = " + dictionary.size());
	}
	
	String wordsToBeDeleted = "aa";
	
	public boolean testNaiveUpdate() throws Exception {
		Trie trieDelete = naiveDelete(wordsToBeDeleted);
		dictionaryMap.remove(wordsToBeDeleted);
		Trie trieWhole = naiveConstruct();
		return trieWhole.root.equals(trieDelete.root);
	}

	void rotate(ArrayList<String> list) {
		int initial_size = list.size();

		int size_to_be_moved = initial_size / split_size;
		if (size_to_be_moved == 0) {
			size_to_be_moved = 1;
		}

		int new_size = initial_size - size_to_be_moved;
		ArrayList<String> newlist = new ArrayList<String>();

		newlist.addAll(list.subList(new_size, initial_size));
		newlist.addAll(list.subList(0, new_size));

		list.clear();
		list.addAll(newlist);
	}

	final int split_size = 5;

	boolean run_epoch(ArrayList<String> list) throws Exception {
		ArrayList<String> newlist;
		for (int i = 0; i < split_size; ++i) {
			int initial_size = list.size();

			int size_to_be_moved = initial_size / split_size;
			if (size_to_be_moved == 0) {
				size_to_be_moved = 1;
			}

			int new_size = initial_size - size_to_be_moved;

			newlist = new ArrayList<String>();
//[*][*][*](*)			
			newlist.addAll(list.subList(0, new_size));
			initialize(newlist);
			if (!testNaiveUpdate()) {
				Utility.writeString("dictionary.txt", newlist);
				System.out.println("successfully shrinking the erroneous dataset!");
				System.out.println("newlist.size() = " + newlist.size());
				return true;
			}
			rotate(list);
		}
		return false;
	}

	public void test_detect_fault() throws Exception {
//		Utility.writeString("dictionary.txt", loadDictionary("dictionary.txt", 40000));
		do {
			ArrayList<String> list = loadDictionary("dictionary.txt");
			if (!run_epoch(list))
				break;
		} while (true);
	}
}
