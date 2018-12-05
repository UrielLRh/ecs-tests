import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MTax implements Constant {

    public MTax() {
    }

    public static List<String> validate(List<XTax> xTaxList) {

        List<String> errorList = new ArrayList<>();

        if (xTaxList != null && xTaxList.size() > 0) {
            List<String> validIds = new ArrayList<>();
            int cont = 0;
            for (XTax tax : xTaxList) {
                if (tax.getId() != null) {
                    validIds.add(tax.getId().toString());
                }

                if (tax.getTax() == null) {
                    errorList.add("El impuesto es obligatorio");
                }

                if (!tax.isLocal()) {
                    cont++;
                }
            }
            if (cont <= 0) {
                errorList.add("Debe de incluir al menos una tasa no local");
            }
            if (validIds.size() > 0) {

                List<XTax> xt = TaxsByListId(validIds, false);
                if (xt.size() != validIds.size()) {
                    errorList.add("Existen datos no guardados previamente");
                } else {
                    HashMap<String, XTax> map_taxs = new HashMap<String, XTax>();
                    for (XTax tax : xt) {
                        map_taxs.put(tax.getId().toString(), tax);
                    }
                    for (int i = 0; i < xTaxList.size(); i++) {
                        if (xTaxList.get(i).getId() != null) {
                            xTaxList.get(i).setCreated(map_taxs.get(xTaxList.get(i).getId().toString()).getCreated());
                        }
                    }
                }
            }
        }

        return errorList;
    }

}
