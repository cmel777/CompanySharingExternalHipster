import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CompanySharingExternalSharedModule } from '../shared';
import { OfwatUserMgmtComponent } from './ofwat-user-management/ofwat-user-management.component';
import { OfwatUserMgmtDetailComponent } from './ofwat-user-management/ofwat-user-management-detail.component';
import { OfwatUserMgmtPermissionsComponent } from './ofwat-user-management/ofwat-user-management-permissions.component';
import { InviteUserComponent } from './invite-user/invite-user.component';
import { InviteUser} from './invite-user/invite-user.service';
<<<<<<< HEAD
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserProfileService } from './user-profile/user-profile.service';
import { MomentModule } from 'angular2-moment';
=======

>>>>>>> aacdb7fc74a89be03ace6101deaf73b2dae0fc89
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserDialogComponent,
    UserDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    JhiMetricsMonitoringModalComponent,
    JhiMetricsMonitoringComponent,
    JhiHealthModalComponent,
    JhiHealthCheckComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    AuditsService,
    JhiConfigurationService,
    JhiHealthService,
    JhiMetricsService,
    LogsService,
    UserResolvePagingParams,
    OfwatUserResolvePagingParams,
    OfwatPendingInviteResolvePagingParams,
    UserResolve,
    UserModalService,
<<<<<<< HEAD
    PendingInvitesComponent,
    PendingInvitesResendComponent
=======
>>>>>>> aacdb7fc74a89be03ace6101deaf73b2dae0fc89
} from './';

@NgModule({
    imports: [
        CompanySharingExternalSharedModule,
        MomentModule,
        RouterModule.forRoot(adminState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        OfwatUserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        OfwatUserMgmtDetailComponent,
        OfwatUserMgmtPermissionsComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        JhiConfigurationComponent,
        JhiHealthCheckComponent,
        JhiHealthModalComponent,
        JhiDocsComponent,
        JhiMetricsMonitoringComponent,
        JhiMetricsMonitoringModalComponent,
        InviteUserComponent,
        UserProfileComponent,
        PendingInvitesComponent,
        PendingInvitesResendComponent
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        JhiHealthModalComponent,
        JhiMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        JhiConfigurationService,
        JhiHealthService,
        JhiMetricsService,
        LogsService,
        UserResolvePagingParams,
        OfwatUserResolvePagingParams,
        OfwatPendingInviteResolvePagingParams,
        UserResolve,
        UserModalService,
        InviteUser,
        UserProfileService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompanySharingExternalAdminModule {}
