package com.diaghealth.utils;

import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.diaghealth.nodes.labtest.LabTestTreeNode;

public class LabTestTreeUtils {

	public static String STR_HEAD = "HEAD";
	public static String STR_HEAD_APPEND = STR_HEAD + "-";
	public static final int MAX_SUB_GROUPS = 4;
	
	public static LabTestTreeNode findNodeInTree(LabTestTreeNode head, String toFind){
		LabTestTreeNode foundNode = null;
		Boolean found = false;
		
		if(head == null || StringUtils.isEmpty(head.getTestGroupName()))
			return null;
		
		if(head.getTestGroupName().equals(toFind))
			return head;
		
		while(!found){
			Set<LabTestTreeNode> children = head.getChildren();
			if(children != null){
				for(LabTestTreeNode child: children){
					foundNode = findNodeInTree(child, toFind);
					if(foundNode != null){
						found = true;
						break;
					}
				}
				return foundNode;
			}else {
				break; //No children, no need to loop again
			}
		}
		return foundNode;
	}
}
