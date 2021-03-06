import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataBundle, DataBundleService, DataCollection, DataCollectionService } from '../../shared';
import { User, UserService } from '../../shared';
import {map} from "rxjs/operator/map";
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-bundle-creation',
    templateUrl: './data-bundle-creation.component.html',
    providers: [DataBundleService, DataCollectionService]
})
export class DataBundleCreationComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataBundleExists: boolean;
    dataBundle: DataBundle;
    users: User[];
    userMap: Map<number, User>;
    private subscription: Subscription;
    dataCollection: DataCollection;
    ownerId: any;
    reviewerId: any;
    currentDate: any;
    ownerIndex: any;
    reviewerIndex: any;

    constructor(
        private alertService: JhiAlertService,
        private dataBundleService: DataBundleService,
        private userService: UserService,
        private route: ActivatedRoute,
        private dataCollectionService: DataCollectionService,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataBundleExists = false;
        this.dataBundle = {};
        this.loadUsers();
        this.currentDate = new Date();
        this.subscription = this.route.params.subscribe((params) => {
            this.loadDataCollection(params['dataCollectionId']);
        });
    }

    ngAfterViewInit() {
    }

    loadDataCollection(dataCollectionId) {
        this.dataCollectionService.get(dataCollectionId).subscribe((dataCollection) => {
            this.dataCollection = dataCollection;
        });
    }


    loadUsers() {
        this.userService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onLoadUsersError(res.json)
        );
    }

    private onLoadUsersSuccess(data, headers) {
        this.users = data;
        this.userMap = new Map<number, User>();
        for (let user of this.users) {
            this.userMap.set(user.id, user);
        }
    }

    private onLoadUsersError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    create() {
        if (this.ownerIndex) {
            const owner = this.userMap.get(parseInt(this.ownerIndex));
            this.dataBundle.ownerId = owner.id;
        }
        else {
            this.dataBundle.ownerId = this.dataCollection.owner.id;
        }
        if (this.reviewerIndex) {
            const reviewer = this.userMap.get(parseInt(this.reviewerIndex));
            this.dataBundle.reviewerId = reviewer.id;
        }
        else {
            this.dataBundle.reviewerId = this.dataCollection.reviewer.id;
        }

        this.dataBundle.dataCollectionId = this.dataCollection.id;
        this.dataBundle.dataCollectionName = this.dataCollection.name;

        this.dataBundleService.create(this.dataBundle).subscribe(
            response => {
                console.log("success" + response.status);
                this.success = true;
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.errorDataBundleExists = true;
                }
                else {
                    this.error = true;
                }
            }
        );
    }
}
