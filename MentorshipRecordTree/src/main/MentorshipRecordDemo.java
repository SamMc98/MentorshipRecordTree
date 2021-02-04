package main;

import lib.MentorshipRecord;

/**
 * The MentorshipRecordDemo classes uses and accesses methods
 * created within the MethodRecord class.
 * This class demonstrates the functionality of some methods.
 * 
 * @author P2536632
 *
 */

public class MentorshipRecordDemo {
	public static void main(String[] args) {

		/**
		 * Creation of the n-ary tree.
		 * A node, it's parent and it's data are created.
		 * Each newly created node, except the root, are 
		 * linked to have a parent node.
		 */
		MentorshipRecord<String> root = new MentorshipRecord<>("Mentorship Records");
		MentorshipRecord<String> student01 = root.addChild(new MentorshipRecord<String>("P2531111"));
		MentorshipRecord<String> student02 = root.addChild(new MentorshipRecord<String>("P2530001"));
		MentorshipRecord<String> student03 = student01.addChild(new MentorshipRecord<String>("P2531120"));
		MentorshipRecord<String> student04 = student01.addChild(new MentorshipRecord<String>("P2532211"));
		MentorshipRecord<String> student05 = student02.addChild(new MentorshipRecord<String>("P2530230"));
		MentorshipRecord<String> student06 = student02.addChild(new MentorshipRecord<String>("P2534141"));
		MentorshipRecord<String> student07 = student03.addChild(new MentorshipRecord<String>("P2530201"));
		MentorshipRecord<String> student08 = student04.addChild(new MentorshipRecord<String>("P2530150"));
		MentorshipRecord<String> student09 = student04.addChild(new MentorshipRecord<String>("P2530190"));
		MentorshipRecord<String> student10 = student04.addChild(new MentorshipRecord<String>("P2530210"));
		MentorshipRecord<String> student11 = student06.addChild(new MentorshipRecord<String>("P2530229")); 
		MentorshipRecord<String> student12 = student06.addChild(new MentorshipRecord<String>("P2530250"));

		/*Prints the demos opening message.*/
		System.out.println("\nA Demonstration of Functionality of the Mentorship Record Tree Data Structure.");
		System.out.println("Each section of the demonstration is seperated with a line for clarity.\n");
		System.out.println("---------------------------------------------------------------------------------");
		
		/*Prints n-ary tree in full from root.*/
		System.out.println("The tree before any modifications:\n");
		MentorshipRecord.printTree(root, " ");

		System.out.println("---------------------------------------------------------------------------------");
		/*Finds the mentees of a mentor, and returns the mentees' P-numbers. 
		 *This example returns P2530230 and P2534141.*/ 
		System.out.println("Find Mentees of Mentor P2530001:\n");
		MentorshipRecord.findMenteesOfMentor(root, "P2530001");

		System.out.println("---------------------------------------------------------------------------------");
		/*Finds the mentor of a mentee, and returns the mentor's P-number.
		 *This example returns P2530001.*/
		System.out.println("Find Mentor of Mentee P2530230:\n");
		MentorshipRecord.findMentorOfMentee(root, "P2530230");

		System.out.println("---------------------------------------------------------------------------------");
		/*Checks to see if a mentee has a mentor assigned.
		 *Checking P2536632 will return nothing, as it doesn't have a mentor.
		 *Checking P2530230 will return that it does have a mentor.*/
		System.out.println("Check if Mentees has Mentor:\n");
		System.out.print("Checking if P2536632 has mentor: ");		
		MentorshipRecord.checkMenteeHasMentor(root, "P2536632");
		System.out.print("\nChecking if P2530230 has mentor: ");
		MentorshipRecord.checkMenteeHasMentor(root, "P2530230");
		
		System.out.println("---------------------------------------------------------------------------------");
		/*Searches for a mentor's existence within the tree.
		 *This example returns the path traversed to located the node.
		 *It also returns true, as it is a mentor and a part of the tree.*/
		System.out.println("Search for Mentor P2534141 within the tree:\n");
		System.out.println("Traversed path:");
		System.out.println("\nSearch to find if mentor exists returns " + MentorshipRecord.searchForMentor(root, "P2534141").isPresent() + ".");
		
		System.out.println("---------------------------------------------------------------------------------");
		/*Creates a new student node 'student13' with a P-number of 'P2536632'.*/		 
		MentorshipRecord<String> student13 = new MentorshipRecord<>("P2536632");
		
		/*Assigns a mentor to the newly created student node.*/
		System.out.println("Assign Mentor P2531120 to P2536632:");
		
		MentorshipRecord.assignMentor(root, "P2531120", student13);
		/*Then checks to see if student13 has a mentor.
		 *Checking P2536632 will return will return that it does have a mentor.*/	
		System.out.print("\nChecking if P2536632 has mentor: ");
		MentorshipRecord.checkMenteeHasMentor(root, "P2536632");
		
		/*Prints n-ary tree in full from root.
		 *It shows P2536632 now added to the tree.*/
		System.out.println("\nP2536632 is now assigned to be mentored by P2531120, as can now be seen:\n");
		MentorshipRecord.printTree(root, " ");
		
		System.out.println("---------------------------------------------------------------------------------");
		/*Removes a mentee from the tree.
		 *Then prints n-ary tree in full from root.
		 *It shows P2530230 is no longer within the tree.*/
		System.out.println("Remove mentee P2530230 from the tree:\n");
		MentorshipRecord.removeMentee(root, "P2530230");
		MentorshipRecord.printTree(root, " ");
		
		System.out.println("---------------------------------------------------------------------------------");
		/*Removes a mentor from the tree and it's descendants.
		 *Then prints n-ary tree in full from root.
		 *It shows P2531120, P2530201 & P2536632 are no longer within the tree.*/
		System.out.println("Remove mentor P2531120 from the tree:\n");
		MentorshipRecord.removeMentor(root, "P2531120");
		MentorshipRecord.printTree(root, " ");
		
		System.out.println("---------------------------------------------------------------------------------");
		/*Removes the root of the tree and it's descendants.
		 *Then prints n-ary tree from root.
		 *It shows that there are no nodes stemming from root.
		 *Only the root is printed*/
		System.out.println("Removes the root and all of its descendants:\n");
		root.removeRoot(); 
		MentorshipRecord.printTree(root, " ");
		
		System.out.println("---------------------------------------------------------------------------------");
		/*Prints the demos closing message.*/
		System.out.println("\nEnd of Demonstration.");
	}
}