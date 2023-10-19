package modele;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IA {
    public String fonctionnement(Map<String, List<String>> c, Plateau p);

    String fonctionnement(Plateau plateau, String coupPrecedent, String historique);

    public String getNom();
    public String getCouleur();
}
