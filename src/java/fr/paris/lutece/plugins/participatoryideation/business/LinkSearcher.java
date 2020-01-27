/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.participatoryideation.business;

import java.sql.Date;

/**
 * This is the business class for storing search infos
 */
public class LinkSearcher
{

    // public static final String COLUMN_ID = "id_link";
    // public static final String COLUMN_CODE_CAMPAGNE = "code_campagne";
    // public static final String COLUMN_CODE_IDEE = "code_idee";
    // public static final String COLUMN_TITLE = "title";

    private String _strCodeCampagne;
    private Integer _nCodeIdee;
    private String _strTitle;

    public String getCodeCampagne( )
    {
        return _strCodeCampagne;
    }

    public void setCodeCampagne( String codeCampagne )
    {
        this._strCodeCampagne = codeCampagne;
    }

    public Integer getCodeIdee( )
    {
        return _nCodeIdee;
    }

    public void setCodeIdee( Integer codeIdee )
    {
        this._nCodeIdee = codeIdee;
    }

    public String getTitle( )
    {
        return _strTitle;
    }

    public void setTitle( String title )
    {
        this._strTitle = title;
    }

}
