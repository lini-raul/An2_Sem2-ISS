package domain;

public class ValidatorCarte {
    public void validate(Carte carte) throws ValidateException{
        String errors = new String("");
        if(carte.getTitlu().equals("") || carte.getTitlu().equals(null))
        {
            errors+="Dati un titlu cartii!\n";
        }
        if(carte.getAutor().equals("") || carte.getAutor().equals(null))
        {
            errors+="Dati un autor cartii!\n";
        }

        if(carte.getNrExemplare() < 1){
            errors+="Dati un numar de exemplare mai mare sau egal cu 1\n";
        }
        if(!errors.equals("")){
            throw new ValidateException(errors);
        }
    }
}
