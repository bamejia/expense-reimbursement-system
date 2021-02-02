package proj.p1.models;

public class ReimbursementToken {
	private int id;
	private int amount;
	private ReimbursementTokenType type;

	public ReimbursementToken(int id, int amount, ReimbursementTokenType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + id;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementToken other = (ReimbursementToken) obj;
		if (amount != other.amount)
			return false;
		if (id != other.id)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ReimbursementTokenType getType() {
		return type;
	}

	public void setType(ReimbursementTokenType type) {
		this.type = type;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ReimbursementToken [id=" + id + ", amount=" + amount + ", type=" + type + "]";
	}
	
}
