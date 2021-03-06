import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';
import { CompanySelectComponent } from './company-select/company-select.component';

import {
    CompanySharingExternalSharedLibsModule,
    CompanySharingExternalSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
/*    LoginService,
    LoginModalService,*/
    Principal,
    HasAnyAuthorityDirective
/*    LoginComponent*/
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedLibsModule,
        CompanySharingExternalSharedCommonModule
    ],
    declarations: [
/*        LoginComponent,*/
        HasAnyAuthorityDirective,
        CompanySelectComponent
    ],
    providers: [
/*        LoginService,
        LoginModalService,*/
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    // entryComponents: [JhiLoginModalComponent],
    exports: [
        CompanySharingExternalSharedCommonModule,
/*        LoginComponent,*/
        HasAnyAuthorityDirective,
        DatePipe,
        CompanySelectComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class CompanySharingExternalSharedModule {}
