from rest_framework import routers, serializers, viewsets

from backend import models


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.User
        fields = ['url', 'username', 'email', 'is_staff', 'last_name', 'first_name', 'second_name']


class CourseSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.Course
        fields = ['url', 'name']


class CourseTopicSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.CourseTopic
        fields = ['url', 'course', 'code', 'name', 'text', 'modified']


class UserViewSet(viewsets.ModelViewSet):
    queryset = models.User.objects.all()
    serializer_class = UserSerializer


class CourseViewSet(viewsets.ModelViewSet):
    queryset = models.Course.objects.all()
    serializer_class = CourseSerializer


class CourseTopicViewSet(viewsets.ModelViewSet):
    queryset = models.CourseTopic.objects.all()
    serializer_class = CourseTopicSerializer


router = routers.DefaultRouter()
router.register(r'users', UserViewSet)
router.register(r'course', CourseViewSet)
router.register(r'course_topic', CourseTopicViewSet)
