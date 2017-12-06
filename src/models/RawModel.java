package models;

public class RawModel {

	private int vertex_count;
	private int vb_id;
	private int ib_id;
	private int nb_id;

	public RawModel(int vbid, int nbid, int ibid, int vc) {
		vertex_count = vc;
		vb_id = vbid;
		nb_id = nbid;
		ib_id = ibid;
	}

	public int getVertexCount() {
		return vertex_count;
	}

	public int getVBID() {
		return vb_id;
	}

	public int getIBID() {
		return ib_id;
	}
	
	public int getNBID(){
		return nb_id;
	}
	
}


