package application;

public class ChatterBot {
	private String[] text = new String[] {
			"Hello!",
			"What is your name?",
			"Where did you go to schol?",
			"What was your GPA in school?",
			"What courses did you take in school?",
			"What projects did you do in school?",
			"Have you done any volutneer work?",
			"What has been your best exprience?",
			"What has been your worst exprince?",
			"What has been your most challenging experience",
			"Why do you want this job?",
			"What are your skills in software?",
			"Do you have any other computer skills?",
			"What is a current goal you have?",
			"Where do you see yourself in ten years?",
			"What's your goal in ten years?",
			"What are your strengths?",
			"What are your weaknesses?",
			"Do you have a hobby?",
			"What's your favourite hoby?",
			"What's your favourite hobby?",
			"Do you have any pets?",
			"Do you have any allgeries?",
			"Do you have any children?",
			"If you could have any superpower, what would it be?",
			"Descirbe your leadership?",
			"Do you believe you're a good fit for this company?",
			"You seem like a cool guy, you're hired!",
			"Are you okay with an annual salary of $1",
			"Do you have any questions?",
			"See you on Monday!",
			"Goodbye!"
	};
	private int index = -1;
	ChatterBot(){}
	public String next() {
		this.index = this.index + 1;
		return text[index];
	}
}
