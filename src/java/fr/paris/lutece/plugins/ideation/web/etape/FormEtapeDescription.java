package fr.paris.lutece.plugins.ideation.web.etape;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.ideation.business.ProfanityFilter;
import fr.paris.lutece.plugins.ideation.service.IdeationProfanityFilter;
import fr.paris.lutece.plugins.profanityfilter.utils.ProfanityResult;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.util.AppLogService;


public class FormEtapeDescription extends  AbstractFormEtape {

	private static final String I18N_ERROR_DESCRIPTION_PROFANITY          = "ideation.validation.idee.FormEtapeDescription.description.profanity";
	private static final String I18N_ERROR_DESCRIPTION_MIN_LENGTH         = "ideation.validation.idee.Description.sizeMin";
	private static final String I18N_ERROR_DESCRIPTION_MAX_LENGTH         = "ideation.validation.idee.Description.sizeMax";
	private static final String I18N_ERROR_HANDICAP_COMPLEMENT_MIN_LENGTH = "ideation.validation.idee.HandicapComplement.sizeMin";
	private static final String I18N_ERROR_HANDICAP_COMPLEMENT_MAX_LENGTH = "ideation.validation.idee.HandicapComplement.sizeMax";
	private static final String I18N_ERROR_OPERATINGBUDGET_MIN_LENGTH     = "ideation.validation.idee.Operatingbudget.sizeMin";
	private static final String I18N_ERROR_OPERATINGBUDGET_MAX_LENGTH     = "ideation.validation.idee.Operatingbudget.sizeMax";
	
	private static final String DSKEY_DESCRIPTION_MIN_LENGTH         = "ideation.site_property.form.description.minLength";
	private static final String DSKEY_DESCRIPTION_MAX_LENGTH         = "ideation.site_property.form.description.maxLength";
	private static final String DSKEY_HANDICAP_COMPLEMENT_MIN_LENGTH = "ideation.site_property.form.handicap_complement.minLength";
	private static final String DSKEY_HANDICAP_COMPLEMENT_MAX_LENGTH = "ideation.site_property.form.handicap_complement.maxLength";
	private static final String DSKEY_OPERATINGBUDGET_MIN_LENGTH     = "ideation.site_property.form.operatingbudget.minLength";
	private static final String DSKEY_OPERATINGBUDGET_MAX_LENGTH     = "ideation.site_property.form.operatingbudget.maxLength";
	
	@NotEmpty( message = "#i18n{ideation.validation.idee.FormEtapeDescription.description.notEmpty}" )
	@Size( max = 10000 , message = "#i18n{ideation.validation.idee.Description.size}" )
	private String _strDescription;
	@Pattern( regexp = "(\\d|\\s){0,20}", message = "#i18n{ideation.validation.idee.FormEtapeDescription.cout.pattern}" )
	private String _strCout;

	// ------------------------------------------------------------

	public String getDescription() {
		return _strDescription;
	}


	public void setDescription(String _strDescription) {
		this._strDescription = _strDescription;
	}


	// ------------------------------------------------------------

	public String getCout() {
		return _strCout;
	}


	public void setCout(String _strCout) {
		this._strCout = _strCout;
	}
	
	// ------------------------------------------------------------

	@NotEmpty( message = "#i18n{ideation.validation.idee.FormEtapeDescription.handicap.notEmpty}" )
	private String _strHandicap;

    public String getHandicap() {
		return _strHandicap;
	}

	public void setHandicap(String strHandicap) {
		this._strHandicap = strHandicap;
	}

	// ------------------------------------------------------------

	private String _strHandicapComplement;

    public String getHandicapComplement() {
		return _strHandicapComplement;
	}

	public void setHandicapComplement(String strHandicapComplement) {
		this._strHandicapComplement = strHandicapComplement;
	}

	// ------------------------------------------------------------

	private String _strOperatingbudget;

    public String getOperatingbudget() {
		return _strOperatingbudget;
	}

	public void setOperatingbudget(String strOperatingbudget) {
		this._strOperatingbudget = strOperatingbudget;
	}

	// ------------------------------------------------------------

    public List<String> checkValidationErrorsLocalized(HttpServletRequest request, Locale locale)
    {
        List<String> listErrors=new ArrayList<>();
        String userUid="guid";
        LuteceUser user = SecurityService.getInstance().getRegisteredUser(request);
        if(user != null)
        {
        	userUid= user.getName( );
        }
        
        ProfanityResult profanityResult = IdeationProfanityFilter.getInstance().scanString(getDescription(), ProfanityFilter.DESCRIPTION_RESOURCE_TYPE, userUid);
        Set<String> swearWords = profanityResult.getSwearWords();
        if (!swearWords.isEmpty()) {
            listErrors.add(I18nService.getLocalizedString(I18N_ERROR_DESCRIPTION_PROFANITY, locale) + StringUtils.join(swearWords.iterator(), ','));
        }

        String strMax = DatastoreService.getDataValue(DSKEY_DESCRIPTION_MAX_LENGTH,"");
        if (!"".equals(strMax)) {
            try {
                int nMax = Integer.parseInt(strMax);
                if (getDescription().trim().replaceAll("(\\r\\n|\\n\\r)", " ").length() > nMax) {
                    listErrors.add(I18nService.getLocalizedString(I18N_ERROR_DESCRIPTION_MAX_LENGTH,
                        new String[] { Integer.toString(nMax) }, locale));
                }
            } catch (NumberFormatException nfe) {
                AppLogService.error("IdeationApp: NumberFormatException when parsing max Description length from datastore, key " + DSKEY_DESCRIPTION_MAX_LENGTH, nfe);
            }
        }

        String strMin = DatastoreService.getDataValue(DSKEY_DESCRIPTION_MIN_LENGTH,"");
        if (!"".equals(strMin)) {
            try {
                int nMin = Integer.parseInt(strMin);
                if (getDescription().trim().length() < nMin) {
                    listErrors.add(I18nService.getLocalizedString(I18N_ERROR_DESCRIPTION_MIN_LENGTH,
                        new String[] { Integer.toString(nMin) }, locale));
                }
            } catch (NumberFormatException nfe) {
                AppLogService.error("IdeationApp: NumberFormatException when parsing max Description length from datastore, key " + DSKEY_DESCRIPTION_MAX_LENGTH, nfe);
            }
        }

        if ( "yes".equals(getHandicap()) ) 
        {
        	String strMinHandicapComplement = DatastoreService.getDataValue(DSKEY_HANDICAP_COMPLEMENT_MIN_LENGTH,"");
            if (!"".equals(strMinHandicapComplement)) {
                try {
                    int nMin = Integer.parseInt(strMinHandicapComplement);
                    if (getHandicapComplement().trim().length() < nMin) {
                        listErrors.add(I18nService.getLocalizedString(I18N_ERROR_HANDICAP_COMPLEMENT_MIN_LENGTH, new String[] { Integer.toString(nMin) }, locale));
                    }
                } catch (NumberFormatException nfe) {
                    AppLogService.error("IdeationApp: NumberFormatException when parsing min HandicapComplement length from datastore, key " + DSKEY_HANDICAP_COMPLEMENT_MIN_LENGTH, nfe);
                }
            }
            
        	String strMaxHandicapComplement = DatastoreService.getDataValue(DSKEY_HANDICAP_COMPLEMENT_MAX_LENGTH,"");
            if (!"".equals(strMaxHandicapComplement)) {
                try {
                    int nMax = Integer.parseInt(strMaxHandicapComplement);
                    if (getHandicapComplement().trim().length() > nMax) {
                        listErrors.add(I18nService.getLocalizedString(I18N_ERROR_HANDICAP_COMPLEMENT_MAX_LENGTH, new String[] { Integer.toString(nMax) }, locale));
                    }
                } catch (NumberFormatException nfe) {
                    AppLogService.error("IdeationApp: NumberFormatException when parsing min HandicapComplement length from datastore, key " + DSKEY_HANDICAP_COMPLEMENT_MAX_LENGTH, nfe);
                }
	        }
        }
        
        if( getOperatingbudget( ).trim().length( ) > 0 )
        {
        	String strMinOperatingbudget = DatastoreService.getDataValue(DSKEY_OPERATINGBUDGET_MIN_LENGTH,"");
            if (!"".equals(strMinOperatingbudget)) {
                try {
                    int nMin = Integer.parseInt(strMinOperatingbudget);
                    if (getOperatingbudget().trim().length() < nMin) {
                        listErrors.add(I18nService.getLocalizedString(I18N_ERROR_OPERATINGBUDGET_MIN_LENGTH,
                            new String[] { Integer.toString(nMin) }, locale));
                    }
                } catch (NumberFormatException nfe) {
                    AppLogService.error("IdeationApp: NumberFormatException when parsing min Operatingbudget length from datastore, key " + DSKEY_OPERATINGBUDGET_MIN_LENGTH, nfe);
                }
            }
            
            String strMaxOperatingbudget = DatastoreService.getDataValue(DSKEY_OPERATINGBUDGET_MAX_LENGTH,"");
            if (!"".equals(strMaxOperatingbudget)) {
                try {
                    int nMax = Integer.parseInt(strMaxOperatingbudget);
                    if (getOperatingbudget().trim().length() > nMax) {
                        listErrors.add(I18nService.getLocalizedString(I18N_ERROR_OPERATINGBUDGET_MAX_LENGTH,
                            new String[] { Integer.toString(nMax) }, locale));
                    }
                } catch (NumberFormatException nfe) {
                    AppLogService.error("IdeationApp: NumberFormatException when parsing max Operatingbudget length from datastore, key " + I18N_ERROR_OPERATINGBUDGET_MAX_LENGTH, nfe);
                }
            }
        }
        
        return listErrors;
    }
	
	
}