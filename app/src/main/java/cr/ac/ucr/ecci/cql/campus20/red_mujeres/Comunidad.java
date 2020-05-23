package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

public class Comunidad
{
    private int communityImgRes;
    private String communityName;
    private String communityNoMemebers;
    private String communityDescription;

    public Comunidad(int communityImgRes, String communityName, String communityNoMemebers, String communityDescription) {
        this.communityImgRes = communityImgRes;
        this.communityName = communityName;
        this.communityNoMemebers = communityNoMemebers;
        this.communityDescription = communityDescription;
    }

    public int getCommunityImgRes() {
        return communityImgRes;
    }

    public void setCommunityImgRes(int communityImgRes) {
        this.communityImgRes = communityImgRes;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityNoMemebers() {
        return communityNoMemebers;
    }

    public void setCommunityNoMemebers(String communityNoMemebers) {
        this.communityNoMemebers = communityNoMemebers;
    }

    public String getCommunityDescription() {
        return communityDescription;
    }

    public void setCommunityDescription(String communityDescription) {
        this.communityDescription = communityDescription;
    }
}
