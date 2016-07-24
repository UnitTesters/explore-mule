%dw 1.0
%output application/csv
---
payload.root.*employee map {
		name: $.fname ++ ' ' ++ $.lname,
			dob: $.dob,
			age: (now as :string {format: "yyyy"}) -  
					(($.dob as :date {format:"MM-dd-yyyy"}) as :string {format:"yyyy"})
}
	