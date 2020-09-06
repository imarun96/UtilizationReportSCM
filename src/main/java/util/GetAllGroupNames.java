package util;

import java.util.Arrays;
import java.util.List;

public class GetAllGroupNames {
	public enum GroupName {
		INVENTORY_MANAGEMENT("Inventory Management"), INVENTORY_RECEIPT("Inventory Receipt & Payment"), HEDGE("Hedge"),
		BUSINESS("Business"), SALES("Sales"), PROJECT_1("Project 1"), PROJECT_2("Project 2"), PROJECT_3("Project 3"),
		PP("PP"), COST("Cost"), QE("QE"), FUNCTIONAL_BT("FBT");

		private String license;

		public String getValue() {
			return license;
		}

		private GroupName(String groupName) {
			this.license = groupName;
		}
	}

	public List<String> getAllGroupNames() {
		return Arrays.asList(GroupName.BUSINESS.getValue(), GroupName.COST.getValue(),
				GroupName.FUNCTIONAL_BT.getValue(), GroupName.HEDGE.getValue(),
				GroupName.INVENTORY_MANAGEMENT.getValue(), GroupName.INVENTORY_RECEIPT.getValue(),
				GroupName.PP.getValue(), GroupName.PROJECT_1.getValue(), GroupName.PROJECT_2.getValue(),
				GroupName.PROJECT_3.getValue(), GroupName.QE.getValue(), GroupName.SALES.getValue());
	}
}