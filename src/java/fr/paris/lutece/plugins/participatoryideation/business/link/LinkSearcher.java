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
package fr.paris.lutece.plugins.participatoryideation.business.link;

/**
 * This is the business class for storing search infos
 */
public class LinkSearcher
{

    // public static final String COLUMN_ID = "id_link";
    // public static final String COLUMN_CODE_CAMPAIGN = "code_campaign";
    // public static final String COLUMN_CODE_PROPOSAL = "code_proposal";
    // public static final String COLUMN_TITLE = "title";

    private String _strCodeCampaign;
    private Integer _nCodeProposal;
    private String _strTitle;

    public String getCodeCampaign( )
    {
        return _strCodeCampaign;
    }

    public void setCodeCampaign( String codeCampaign )
    {
        this._strCodeCampaign = codeCampaign;
    }

    public Integer getCodeProposal( )
    {
        return _nCodeProposal;
    }

    public void setCodeProposal( Integer codeProposal )
    {
        this._nCodeProposal = codeProposal;
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
