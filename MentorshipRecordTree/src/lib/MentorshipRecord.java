package lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The MentorshipRecord class creates and define methods
 * to be implemented within the MentorshipRecord Demo class.
 * Not all methods within this class will be demonstrated.
 * 
 * @author P2536632
 *
 * The class is of generic type E.
 * @param <E>
 */

public class MentorshipRecord<E> {

	private E data = null;
	private MentorshipRecord<E> parent = null;
	private List<MentorshipRecord<E>> children = new ArrayList<>();

	/**
	 * Constructor used to create a new node object.  
	 * 
	 * @param data is used to specify a student's P-number. It must start with a 'P' 
	 * and contain seven numbers. Enter as a String. Only root does not have a P-number.
	 */
	public MentorshipRecord(E data) {
		this.data = data;
	}	

	/**
	 * Constructor used to create a new node object, and define a hierarchical link 
	 * between itself and it's parent node. 
	 * 
	 * @param data is used to specify a student's P-number. It must start with a 'P' 
	 * and contain seven numbers. Enter as a String.
	 * @param parent is used to specify which node the new node is directly descendant 
	 * from. It must be the name of a node that already exists. 
	 */
	public MentorshipRecord(E data, MentorshipRecord<E> parent) {
		this.data = data;
		this.parent = parent;
	}

	/** 
	 *Returns the root of a given node. Join the getData() method to view it's
	 *String value
	 *
	 * @return the root of a given node.
	 */
	public MentorshipRecord<E> getRoot() {
		if(parent == null){
			return this;
		}
		return parent.getRoot();
	}

	/**
	 * Returns the String value of a given node. Can be used to pass data as 
	 * a String.
	 * 
	 * @return the String value of a node.
	 */
	public E getData() {
		return data;
	}

	/**
	 * Returns the parent node of a given node. 
	 * It will return null if the given node is root. 
	 * Join the getData() method to view it's String value.
	 * 
	 * @return the node's parent node.
	 */
	public MentorshipRecord<E> getParent() {
		return parent;
	}

	/**
	 * Returns the arraylist of generated children. Use 
	 * a loop or method to view their String value. 
	 * 
	 * @return the arraylist of children.
	 */
	public List<MentorshipRecord<E>> getChildren() {
		return children;
	}

	/**
	 * Sets new String for the data field as invoked on a data field. 
	 * 
	 * @param data The String value of a node object. Can be used for the P-number. 
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * Sets new parent node for the invoked parent field.
	 * 
	 * @param parent The parent of a given node.
	 */
	private void setParent(MentorshipRecord<E> parent) {
		this.parent = parent;
	}

	/**
	 * Adds a created list of children to the arraylist Children.
	 * The parent node which the method is invoked on is set to be 
	 * the parent of each child node within the list.
	 * 
	 * @param children A list of child nodes.
	 */
	public void addChildren(List<MentorshipRecord<E>> children) {
		children.forEach(eachChild -> eachChild.setParent(this));
		this.children.addAll(children);
	}

	/**
	 * Adds a new child object to the children arraylist. 
	 * Will return the new child from the list. 
	 * 
	 * @param child The node child to be added.
	 * @return The child.
	 */
	public MentorshipRecord<E> addChild(MentorshipRecord<E> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	/**
	 * Adds a new object node to the tree by creating a link between parent and newNode params. 
	 * 
	 * @param parent The parent node that the child will be directly linked to.
	 * @param newNode The new child node object to add to the tree.
	 */
	public static void addNewNodeToTree(MentorshipRecord<String> parent, MentorshipRecord<String> newNode) {
		if (parent != null && newNode != null) {
			parent.addChild(newNode);
		}
	}

	/**
	 * Removes root node from the invoked tree, and therefore removes all children of root. 
	 * 
	 * @return newParent, which is an empty tree with no nodes or links. 
	 * @throws IllegalStateException when a root has not been used to invoke the method.
	 */
	public MentorshipRecord<E> removeRoot() {
		if (parent != null) { 
			throw new IllegalStateException("The tree's root has not be stated");
		}
		MentorshipRecord<E> newParent = null;
		if (!getChildren().isEmpty()) { 
			newParent = getChildren().get(0);
			newParent.setParent(null);
			getChildren().remove(0);
			for (MentorshipRecord<E> eachChild : getChildren()) {
				eachChild.setParent(newParent);
			}
			newParent.getChildren().addAll(getChildren());
		}
		this.getChildren().clear();
		return newParent;
	}

	/**
	 * Removes the chosen node from the children arraylist and all of the nodes descendants. 
	 * If root is chosen, an error will be thrown.  
	 */
	public void removeChosenNode() { 
		if (parent != null) {
			int index = this.parent.getChildren().indexOf(this);
			this.parent.getChildren().remove(this);
			for (MentorshipRecord<E> eachChild : getChildren()) {
				eachChild.setParent(this.parent);
			}
			this.parent.getChildren().addAll(index, this.getChildren());
		} else {
			removeRoot();
		}
		this.getChildren().clear();
	}

	/**
	 * Removes a specified mentor node, using the mentorsString param.
	 * It's descendants are also removed if it is both a mentee and a mentor.  
	 * If the specified node is not a mentor, the operation will not function.
	 * Pushes all relevant nodes to be deleted to a list (ToBeRemoved), which 
	 * is passed onto the removeSelected() method.
	 * 
	 * @param startSearchAt States where the search should begin. Recommended to be a parent node.
	 * @param mentorsString The P-number of the mentor to be deleted.
	 */
	public static void removeMentor(MentorshipRecord<String> startSearchAt, String mentorsString) {

		List<MentorshipRecord<String>> ToBeRemoved = new ArrayList<>();

		if (startSearchAt != null) {
			for (MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				removeMentor(eachChild, mentorsString);
				if (eachChild.getData().equals(mentorsString) && MentorshipRecord.isMentor(eachChild) == true) {
					ToBeRemoved.add(eachChild);
					removeSelected(eachChild, ToBeRemoved);
					if (ToBeRemoved.contains(eachChild)) {
						break;
					}
				}
			}
		}
	}

	/**
	 *  Removes a specified mentee node, using the menteesString param.
	 * It's descendants are also removed if it is both a mentee and a mentor.  
	 * If the specified node is not a menteee, the operation will not function.
	 * Pushes all relevant nodes to be deleted to a list (ToBeRemoved), which 
	 * is passed onto the removeSelected() method.
	 * 
	 * @param startSearchAt States where the search should begin. Recommended to be a parent node.
	 * @param menteesString The P-number of the mentee to be deleted.
	 */
	public static void removeMentee(MentorshipRecord<String> startSearchAt, String menteesString) {

		List<MentorshipRecord<String>> ToBeRemoved = new ArrayList<>();

		if (startSearchAt != null) {
			for (MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				removeMentee(eachChild, menteesString);
				if (eachChild.getData().equals(menteesString) && MentorshipRecord.isMentee(eachChild) == true) {
					ToBeRemoved.add(eachChild);
					removeSelected(eachChild, ToBeRemoved);
					if (ToBeRemoved.contains(eachChild)) {
						break;
					}
				}
			}
		}
	}


	/**
	 * Removes all leaf nodes descending from a specified node. Implements 
	 * the removeAllLeafNodes private method. 
	 */
	public void removeAllLeafNodes(){
		List<MentorshipRecord<E>> childrenToRemove = new ArrayList<>();
		removeAllLeafNodes(this, childrenToRemove);
		childrenToRemove.forEach(MentorshipRecord::removeChosenNode);
	}

	/**
	 * Removes all leaf nodes descending from a specified node using currentNode 
	 * and childrenToRemove args passed from the public removeAllLeafNodes method. 
	 *  
	 * @param currentNode The starting node to remove leaf nodes from.
	 * @param childrenToRemove A list of children that are removed.
	 */
	private void removeAllLeafNodes(MentorshipRecord<E> currentNode, List<MentorshipRecord<E>> childrenToRemove) {
		currentNode.getChildren().forEach(eachChild ->  {
			if(eachChild.getChildren().isEmpty()) {
				childrenToRemove.add(eachChild);
				return;
			}
			removeAllLeafNodes(eachChild, childrenToRemove);
		});
	}

	/**
	 * Removes nodes meeting specified conditions after they have been pushed to a separate list.
	 * 
	 * @param startSearchAt States where the search should begin. Recommended to be a parent node.
	 * @param ToBeRemoved A list of nodes to be deleted from the tree. 
	 */
	private static void removeSelected(MentorshipRecord<String> startSearchAt, List<MentorshipRecord<String>> ToBeRemoved) {
		for (MentorshipRecord<String> eachChild : ToBeRemoved) {
			eachChild.removeAllLeafNodes();
			eachChild.removeChosenNode();
		}
	}

	/**
	 * Assigns a given mentor to a given mentee using the params. 
	 * The operation will not function if a mentor or mentee is null or if the mentor is not a mentor. 
	 * Use this method to add a newly created node to the tree. 
	 * 
	 * @param startSearchAt States where the search should begin. Recommended to be a parent node.
	 * @param mentorsString The P-number of the mentor to be assigned to a given mentee.
	 * @param newMenteeToAssign The newly created mentee to be assigned to the mentor.
	 * @throws IllegalStateException when the entered nodes are null.
	 */
	public static void assignMentor(MentorshipRecord<String> startSearchAt, String mentorsString, MentorshipRecord<String> newMenteeToAssign) {		
		if (startSearchAt != null && newMenteeToAssign != null) {
			for (MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				assignMentor(eachChild, mentorsString, newMenteeToAssign);
				if (eachChild.getData().equals(mentorsString) && MentorshipRecord.isMentor(eachChild) == true && MentorshipRecord.isMentee(newMenteeToAssign) == true) {
					eachChild.addChild(newMenteeToAssign);
				}
			}
		} else {
			throw new IllegalStateException("Please enter a student which exists!");
		}
	}

	/**
	 * Reassigns a new specified mentor to a given mentee. Removes the old instance of it.
	 * 
	 * @param startSearchAt States where the search should begin. Recommended to be a parent node.
	 * @param reassignedMentor P-number's String used to specify the mentor to be reassigned.
	 * @param newMentee Object node used to state the mentees to be mentored by the new mentor. 
	 */
	public static void reassignMentor(MentorshipRecord<String> startSearchAt, String reassignedMentor, MentorshipRecord<String> newMentee) {		
		if (startSearchAt != null) {
			for (MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				reassignMentor(eachChild, reassignedMentor, newMentee);

				if (eachChild.getData().equals(reassignedMentor) && MentorshipRecord.isMentor(eachChild) == true && MentorshipRecord.isMentee(newMentee) == true) {
					MentorshipRecord.removeMentee(startSearchAt, newMentee.getData());
					eachChild.addChild(newMentee); 
				}
			}

		}
	}

	/**
	 * Reassigns a new specified mentee to a given mentor. Removes the old instance of it.
	 * 
	 * @param startSearchAt States where the search should begin. Recommended to be a parent node.
	 * @param newMentor Object node used to state the mentor to be mentor the new mentee.
	 * @param reassignedMentee P-number's String used to specify the mentee to be reassigned.
	 */
	public static void reassignMentee(MentorshipRecord<String> startSearchAt, MentorshipRecord<String> newMentor, String reassignedMentee) {		
		if (startSearchAt != null) {
			for (MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				reassignMentee(eachChild, newMentor, reassignedMentee);

				if (eachChild.getData().equals(reassignedMentee) && MentorshipRecord.isMentee(eachChild) == true && MentorshipRecord.isMentor(newMentor) == true) {
					MentorshipRecord.removeMentee(startSearchAt, eachChild.getData());
					newMentor.addChild(eachChild); 
				}
			}

		}
	}

	/**
	 * Searches the tree for data from one given node using the startSearchFrom param
	 * until it finds the String value passed using the searchFor param.
	 * Returns true if there is a match and outputs all nodes within the traversed paths. 
	 * Returns false if there is no match and outputs all nodes within the traversed paths. 
	 * 
	 * @param <E>
	 * @param startSearchFrom The node which the search will begin from. Recommended to be a parent node.
	 * @param searchFor The P-number string to find a match with.
	 * @return True or false depending on the match with all nodes from the traversed paths.
	 */
	private static <E> Optional<?> searchTreeForData(MentorshipRecord<?> startSearchFrom, E searchFor) {
		if(startSearchFrom.getData().equals(searchFor)) {
			return Optional.of(startSearchFrom);
		}
		for(MentorshipRecord<?> eachChild : startSearchFrom.getChildren()) {
			System.out.println(eachChild.getData());
			Optional<?> searchTreeForData = searchTreeForData(eachChild, searchFor);
			if(searchTreeForData.isPresent()) {
				return searchTreeForData;
			}
		}
		return Optional.empty();
	}

	/**
	 * Searches the tree for data from one given node using the startSearchAt param
	 * until it finds the String value passed using the searchFor param.
	 * Returns true if there is a match and outputs all nodes within the traversed paths. 
	 * Returns false if there is no match and outputs all nodes within the traversed paths. 
	 * If the node specified is not a mentor, the operation will return false. 
	 * 
	 * @param <E>
	 * @param startSearchAt The node which the search will begin from. Recommended to be a parent node.
	 * @param searchFor The P-number string to find a match with. Must belong to a mentor. 
	 * @return True or false depending on the match with all nodes from the traversed paths.
	 */
	public static <E> Optional<?> searchForMentor(MentorshipRecord<String> startSearchAt, String searchFor) { 
		if(startSearchAt.getData().equals(searchFor)) {
			return Optional.of(startSearchAt);
		}
		else {
			for(MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				System.out.println("*" + eachChild.getData());
				Optional<?> searchForMentor = searchForMentor(eachChild, searchFor);
				if(searchForMentor.isPresent()) {
					if (MentorshipRecord.isMentor(eachChild) == true) {
						return Optional.of(startSearchAt);
					}
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Finds and outputs all mentee children of a given mentor.
	 * If the mentor param is not a mentor or is root, the operation will not function. 
	 * 
	 * @param startSearchAt The node which the search will begin from. Recommended to be a parent node. 
	 * @param searchFor The P-number string to find a match with. Must belong to a mentor.
	 * @return All of the mentees belonging to a mentor.
	 */
	public static Optional<?> findMenteesOfMentor(MentorshipRecord<String> startSearchAt, String searchFor) {
		if (startSearchAt != null) {
			if (MentorshipRecord.isMentor(startSearchAt) && !MentorshipRecord.isRoot(startSearchAt)) { 
				if(startSearchAt.getData().equals(searchFor)) {
					System.out.println(startSearchAt.getData() + "'s mentees are:");
					for(MentorshipRecord<?> eachChild : startSearchAt.getChildren()) {
						System.out.println("*" + eachChild.getData());
					}	  
				}
			} 
			for(MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				Optional<?> findMenteesOfMentor = findMenteesOfMentor(eachChild, searchFor);
				if(!findMenteesOfMentor.isPresent()) {
					return findMenteesOfMentor;
				}
			} 
		}
		return Optional.of(startSearchAt);
	}

	/**
	 * Finds and outputs the mentor of a given mentee.
	 * If the mentee param is not a mentee or is root, the operation will not function.
	 * 
	 * @param startSearchAt The node which the search will begin from. Recommended to be a parent node.
	 * @param searchFor The P-number string to find a match with. Must belong to a mentee.
	 * @return The mentor linked to a mentee.
	 */
	public static Optional<?> findMentorOfMentee(MentorshipRecord<String> startSearchAt, String searchFor) {
		if (startSearchAt != null) { 	
			if(startSearchAt.getData().equals(searchFor)) {
				if (MentorshipRecord.isMentee(startSearchAt) && !startSearchAt.getParent().getData().equals("Mentorship Records")) {
					System.out.println(startSearchAt.getData() +  "'s mentor is " + startSearchAt.getParent().getData());	  
				}	
			} 
			for(MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				findMentorOfMentee(eachChild, searchFor);
			}
		}
		return Optional.of(startSearchAt);
	}

	/**
	 * Checks if a given mentee is assigned a mentor. 
	 * If the given node is not a mentee, the operation will not function.
	 * 
	 * @param startSearchAt The node which the search will begin from. Recommended to be a parent node. 
	 * @param searchFor The P-number string to find a match with. Must belong to a mentee.
	 * @return The mentors P-number if true and nothing if false. 
	 */
	public static Optional<?> checkMenteeHasMentor(MentorshipRecord<String> startSearchAt, String searchFor) {
		if (startSearchAt != null) { 	
			if(startSearchAt.getData().equals(searchFor)) {
				if(MentorshipRecord.isMentee(startSearchAt) && !startSearchAt.getParent().getData().isEmpty() && !startSearchAt.getParent().getData().equals("Mentorship Records")) {
					System.out.println("The entered mentee " + startSearchAt.getData() + " does have a mentor.");	
				}	
			} 
			for(MentorshipRecord<String> eachChild : startSearchAt.getChildren()) {
				checkMenteeHasMentor(eachChild, searchFor);
			}
		}
		return Optional.of(startSearchAt);
	}

	/**
	 * Finds if a given node is the root of the tree. 
	 * Returns true if the current node is the root of the tree, returns false if not.
	 *  
	 * @param currentNode The node used to find if itself is root or not.
	 * @return True or false depending if the node is root or not.
	 */
	public static boolean isRoot(MentorshipRecord<String> currentNode) {
		if (currentNode.getData().equals("Mentorship Records")) {
			return true;
		}
		return false;	
	}

	/**
	 * Finds if a given node is a mentor.
	 * Returns true if the current node is a mentor, returns false if not.
	 * 
	 * @param currentNode The node used to find if itself is a mentor or not.
	 * @return True or false depending if the node is a mentor or not. 
	 */
	public static boolean isMentor(MentorshipRecord<String> currentNode) {
		if (!MentorshipRecord.isRoot(currentNode)) {
			for(MentorshipRecord<?> eachChild : currentNode.getChildren()) {
				return true;
			}
		}
		else if (MentorshipRecord.isRoot(currentNode)) {
			return false;
		}
		return false;	
	}

	/**
	 * Finds if a given node is a mentee.
	 * Returns true if the current node is a mentee, returns false if not.
	 * 
	 * @param currentNode The node used to find if itself is a mentee or not.
	 * @return True or false depending if the node is a mentor or not.
	 */
	public static boolean isMentee (MentorshipRecord<String> currentNode) {
		if (currentNode != null && currentNode.getParent() != null) {
			if (currentNode.getParent().getData().equals("Mentorship Records") || currentNode.getData().equals("Mentorship Records")) {
				return false;
			} 
		}
		else if (currentNode.getData().equals("Mentorship Records")) {
			return false;
		}
		return true; 
	}

	/**
	 * Prints out the entire tree starting from the given root node, 
	 * indenting a set amount with each generation.
	 * Can also be used to print subtrees, by passing a subtree's root node as an arg.  
	 * 
	 * @param <E>
	 * @param root Where the tree will start printing from. Change from root to print subtrees.
	 * @param indentationType The characters used to visually output each generation's indentation. 
	 */
	public static <E> void printTree(MentorshipRecord<E> root, String indentationType) {
		System.out.println(indentationType + root.getData());
		root.getChildren().forEach(eachChild ->  printTree(eachChild, indentationType + indentationType));
	}
}