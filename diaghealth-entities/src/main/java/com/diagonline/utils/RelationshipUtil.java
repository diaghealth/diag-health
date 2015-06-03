package com.diagonline.utils;


public class RelationshipUtil {
	public static String getRelationship(UserType user, UserType related){
		
		String relation = "";
		switch(user){
			case DOCTOR:
			{
				switch(related){
					case PATIENT:
						relation="DOC_PATIENT_REL";
						break;
					case LAB:
						relation="DOC_LAB_REL";
						break;
					case CLINIC:
						relation="DOC_CLINIC_REL";
						break;
					default:
						break;
				}
			}
			break;
			case CLINIC:
			{
				switch(related){
				case PATIENT:
					relation="CLIINIC_PATIENT_REL";
					break;
				case LAB:
					relation="CLINIC_LAB_REL";
					break;
				case DOCTOR:
					relation="DOC_CLINIC_REL";
					break;
				default:
					break;
				}
			}
			break;
			case LAB:
			{
				switch(related){
				case PATIENT:
					relation="LAB_PATIENT_REL";
					break;
				case CLINIC:
					relation="CLINIC_LAB_REL";
					break;
				case DOCTOR:
					relation="DOC_LAB_REL";
					break;
				default:
					break;
				}
			}
			break;
			
			case PATIENT:
			{
				switch(related){
				case LAB:
					relation="LAB_PATIENT_REL";
					break;
				case CLINIC:
					relation="CLINIC_PATIENT_REL";
					break;
				case DOCTOR:
					relation="DOC_PATIENT_REL";
					break;
				default:
					break;
				}
			}
			break;
			
			default:
				break;
		
		
		}
		return relation;
		
	}
}
