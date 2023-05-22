package domain;

public class ValidatorImprumut {
    public void validate(Imprumut imprumut) throws ValidateException{
        String errors = new String("");
        if(imprumut.getNrExemplare() < 1){
            errors+="Dati un numar de exemplare mai mare sau egal cu 1\n";
        }
        if(!errors.equals("")){
            throw new ValidateException(errors);
        }
    }
}
